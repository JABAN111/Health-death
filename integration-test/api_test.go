package integration_test

import (
	"bytes"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/stretchr/testify/require"
	"io"
	"net/http"
	"testing"
	"time"
)

const (
	address  = "http://localhost:8080"
	authBase = "/auth"
)

var client = http.Client{
	Timeout: 10 * time.Minute,
}

func TestPreflight(t *testing.T) {
	require.Equal(t, true, true)
}

// FIXME чуть позже обрастет деталями, тк userservice(микросервис) потребует инфы
type RegistrationBody struct {
	Email    string `json:"email,omitempty"`
	Password string `json:"password,omitempty"`
}

type LoginBody struct {
	Email    string `json:"email,omitempty"`
	Password string `json:"password,omitempty"`
}

type JwtAuthenticationResponse struct {
	Token string `json:"token"`
}

type ErrHttp struct {
	code int
	text string
}

func (e ErrHttp) Error() string {
	return fmt.Sprintf("{code: %v, error: %v}", e.code, e.text)
}

func signUp(reg RegistrationBody) (*JwtAuthenticationResponse, error) {
	url := address + authBase + "/sign-up"

	requestBody, err := json.Marshal(reg)
	if err != nil {
		return nil, fmt.Errorf("ошибка маршалинга запроса: %w", err)
	}

	req, err := http.NewRequest(http.MethodPost, url, bytes.NewBuffer(requestBody))
	if err != nil {
		return nil, fmt.Errorf("ошибка создания запроса: %w", err)
	}
	req.Header.Set("Content-Type", "application/json")

	resp, err := client.Do(req)
	if err != nil {
		return nil, fmt.Errorf("ошибка выполнения запроса: %w", err)
	}
	defer resp.Body.Close()

	if resp.StatusCode > 300 || resp.StatusCode < 200 {
		bodyBytes, _ := io.ReadAll(resp.Body)
		return nil, ErrHttp{
			code: resp.StatusCode,
			text: string(bodyBytes),
		}
	}

	var applicationResponse JwtAuthenticationResponse
	err = json.NewDecoder(resp.Body).Decode(&applicationResponse)
	if err != nil {
		return nil, fmt.Errorf("ошибка декодирования ответа: %w", err)
	}

	return &applicationResponse, nil
}

func signIn(reg LoginBody) (*JwtAuthenticationResponse, error) {
	url := address + authBase + "/sign-in"

	requestBody, err := json.Marshal(reg)
	if err != nil {
		return nil, fmt.Errorf("ошибка маршалинга запроса: %w", err)
	}
	req, err := http.NewRequest(http.MethodPost, url, bytes.NewBuffer(requestBody))
	if err != nil {
		return nil, fmt.Errorf("ошибка создания запроса: %w", err)
	}
	req.Header.Set("Content-Type", "application/json")

	resp, err := client.Do(req)
	if err != nil {
		return nil, fmt.Errorf("ошибка выполнения запроса: %w", err)
	}
	defer resp.Body.Close()

	if resp.StatusCode < 200 || resp.StatusCode > 300 {
		bodyBytes, _ := io.ReadAll(resp.Body)
		return nil, fmt.Errorf("неожиданный код ответа: %d, тело: %s", resp.StatusCode, string(bodyBytes))
	}

	var jwtAuthResponse JwtAuthenticationResponse
	err = json.NewDecoder(resp.Body).Decode(&jwtAuthResponse)
	if err != nil {
		return nil, fmt.Errorf("ошибка декодирования ответа: %w", err)
	}

	return &jwtAuthResponse, nil
}

func TestSignUp(t *testing.T) {
	testCases := []struct {
		message    string
		body       RegistrationBody
		expErr     bool
		expErrCode int
	}{
		{
			message: "full valid test",
			body: RegistrationBody{
				Email:    "jaba@jaba.jaba",
				Password: "jaba",
			},
			expErr:     false,
			expErrCode: 0,
		},
		{
			message:    "missed data in body",
			body:       RegistrationBody{},
			expErr:     false,
			expErrCode: 0,
		},
		{
			message: "missed password",
			body: RegistrationBody{
				Email: "jaba@jaba.jaba",
			},
			expErr:     false,
			expErrCode: 0,
		},
		{
			message: "missed mail",
			body: RegistrationBody{
				Password: "jaba@jaba.jaba",
			},
			expErr:     false,
			expErrCode: 0,
		},
	}

	for _, tc := range testCases {
		t.Run(tc.message, func(t *testing.T) {
			_, err := signUp(tc.body)
			if tc.expErr {
				require.Error(t, err, tc.message)
				var htErr ErrHttp
				if !errors.As(err, &htErr) {
					require.Equal(t, tc.expErrCode, htErr)
				} else {
					t.Logf("expect http error code %v", tc.expErrCode)
					t.Fail()
				}
			}
		})
	}
}

func TestSignIn(t *testing.T) {
	testCases := []struct {
		message    string
		body       LoginBody
		expErr     bool
		expErrCode int
	}{
		{
			message: "full valid test",
			body: LoginBody{
				Email:    "jaba@jaba.jaba",
				Password: "jaba",
			},
			expErr:     false,
			expErrCode: 0,
		},
		{
			message:    "missed data in body",
			body:       LoginBody{},
			expErr:     false,
			expErrCode: 0,
		},
		{
			message: "missed password",
			body: LoginBody{
				Email: "jaba@jaba.jaba",
			},
			expErr:     false,
			expErrCode: 0,
		},
		{
			message: "missed mail",
			body: LoginBody{
				Password: "jaba@jaba.jaba",
			},
			expErr:     false,
			expErrCode: 0,
		},
	}

	for _, tc := range testCases {
		t.Run(tc.message, func(t *testing.T) {
			_, err := signIn(tc.body)
			if tc.expErr {
				require.Error(t, err, tc.message)
				var htErr ErrHttp
				if !errors.As(err, &htErr) {
					require.Equal(t, tc.expErrCode, htErr)
				} else {
					t.Logf("expect http error code %v", tc.expErrCode)
					t.Fail()
				}
			}
		})
	}

	t.Run("Valid sign in after sign up", func(t *testing.T) {
		RegBody := RegistrationBody{
			Email:    "jaba@jaba.jaba",
			Password: "jaba",
		}

		_, err := signUp(RegBody)
		require.NoError(t, err)

		RegLogin := LoginBody{
			Email:    RegBody.Email,
			Password: RegBody.Password,
		}
		_, err = signIn(RegLogin)
		require.NoError(t, err)
	})

	t.Run("Invalid password attempt", func(t *testing.T) {
		RegBody := RegistrationBody{
			Email:    "jaba@jaba.jaba",
			Password: "jaba",
		}

		_, err := signUp(RegBody)
		require.NoError(t, err)

		RegLogin := LoginBody{
			Email:    RegBody.Email,
			Password: RegBody.Password + "garbage data for err",
		}
		_, err = signIn(RegLogin)
		require.Error(t, err, "there must be err -> passwords are not save")
	})

}
