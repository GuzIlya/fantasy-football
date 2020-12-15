package by.guz.fantasy.football.security.impl;

import by.guz.fantasy.football.configuration.AppConfiguration;
import by.guz.fantasy.football.dto.TokenDto;
import by.guz.fantasy.football.entity.UserEntity;
import by.guz.fantasy.football.entity.enums.UserRoleEntity;
import by.guz.fantasy.football.security.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

import static by.guz.fantasy.football.entity.enums.UserRoleEntity.fromValue;
import static by.guz.fantasy.football.mapper.UserMapper.USER_MAPPER;
import static io.jsonwebtoken.io.Decoders.BASE64;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProviderImpl implements TokenProvider {

    private static final int MILLIS_IN_SECOND = 1000;

    private static final String TYPE_KEY = "typ";
    private static final String ROLE_KEY = "rol";

    private static final String ACCESS_TOKEN = "access";
    private static final String REFRESH_TOKEN = "refresh";

    private final AppConfiguration appConfiguration;

    private Key key;

    private long accessTokenValidityMillis;
    private long refreshTokenValidityMillis;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(BASE64.decode(appConfiguration.getJwt().getBase64Secret()));
        accessTokenValidityMillis = appConfiguration.getJwt().getAccessTokenValidity() * MILLIS_IN_SECOND;
        refreshTokenValidityMillis = appConfiguration.getJwt().getRefreshTokenValidity() * MILLIS_IN_SECOND;
    }

    @Override
    public TokenDto.Default createToken(final UserEntity userEntity) {
        return createToken(USER_MAPPER.toUserDetails(userEntity));
    }

    @Override
    public TokenDto.Default createToken(final UserDetails userDetails) {
        var currentTimeMillis = System.currentTimeMillis();
        var issuedAt = new Date(currentTimeMillis);
        var accessExpiresIn = new Date(currentTimeMillis + accessTokenValidityMillis);
        var refreshExpiresIn = new Date(currentTimeMillis + refreshTokenValidityMillis);
        var userDetailsImpl = (UserDetailsImpl) userDetails;

        var accessToken = createToken(userDetailsImpl, issuedAt, accessExpiresIn, ACCESS_TOKEN);
        var refreshToken = createToken(userDetailsImpl, issuedAt, refreshExpiresIn, REFRESH_TOKEN);
        return TokenDto.Default.builder()
                .issuedAt(issuedAt.getTime())
                .accessToken(accessToken)
                .accessExpiresIn(accessExpiresIn.getTime())
                .refreshToken(refreshToken)
                .refreshExpiresIn(refreshExpiresIn.getTime())
                .build();
    }

    private String createToken(final UserDetailsImpl userDetails,
                               final Date issuedAt,
                               final Date expiresIn,
                               final String tokenType) {
        return Jwts.builder()
                .setSubject(userDetails.getUserId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresIn)
                .claim(TYPE_KEY, tokenType)
                .claim(ROLE_KEY, userDetails.getRole().toString())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public TokenDto.Default refreshToken(final String refreshToken) {
        var userDetails = buildUserDetailsByToken(refreshToken);
        return createToken(userDetails);
    }

    @Override
    public UserDetails buildUserDetailsByToken(final String token) {
        var role = getUserRoleByToken(token);
        var userId = getUserIdByToken(token);
        return USER_MAPPER.toUserDetails(userId, role);
    }

    @Override
    public Long getUserIdByToken(final String token) {
        return Long.parseLong(Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject());
    }

    @Override
    public UserRoleEntity getUserRoleByToken(final String token) {
        return fromValue((String) Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().get(ROLE_KEY));
    }

}
