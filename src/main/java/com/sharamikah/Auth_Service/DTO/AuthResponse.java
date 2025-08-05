package com.sharamikah.Auth_Service.DTO;


import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the payload returned after a successful authentication or refresh.
 * Contains a new access token and (optionally rotated) refresh token.
 */
public final class AuthResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String accessToken;
    private final String refreshToken;
    private final String ipAddress;
    private final String userAgent;
    private final String deviceName;

    /**
     * @param accessToken  non-null JWT to be used for subsequent API calls
     * @param refreshToken non-null opaque token to obtain future access tokens
     */
    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken  = Objects.requireNonNull(accessToken,  "accessToken must not be null");
        this.refreshToken = Objects.requireNonNull(refreshToken, "refreshToken must not be null");
    }

    /**
     * @return JWT for authenticating API requests
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @return opaque refresh token for renewing the access token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthResponse)) return false;
        AuthResponse that = (AuthResponse) o;
        return accessToken.equals(that.accessToken) &&
                refreshToken.equals(that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, refreshToken);
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "accessToken='[PROTECTED]'" +
                ", refreshToken='[PROTECTED]'" +
                '}';
    }

    /**
     * @param ipAddress  client’s IP (could be forwarded via X-Forwarded-For)
     * @param userAgent  User-Agent header value
     * @param deviceName custom device name header (e.g. “X-Device-Name”)
     */
    public CreateDeviceSessionRequest(
            String ipAddress,
            String userAgent,
            String deviceName
    ) {
        this.ipAddress  = Objects.requireNonNull(ipAddress,  "ipAddress must not be null");
        this.userAgent  = Objects.requireNonNull(userAgent,  "userAgent must not be null");
        this.deviceName = Objects.requireNonNull(deviceName, "deviceName must not be null");
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateDeviceSessionRequest)) return false;
        CreateDeviceSessionRequest that = (CreateDeviceSessionRequest) o;
        return ipAddress.equals(that.ipAddress) &&
                userAgent.equals(that.userAgent) &&
                deviceName.equals(that.deviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress, userAgent, deviceName);
    }

    @Override
    public String toString() {
        return "CreateDeviceSessionRequest{" +
                "ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
