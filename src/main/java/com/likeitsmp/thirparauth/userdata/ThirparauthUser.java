package com.likeitsmp.thirparauth.userdata;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

public final class ThirparauthUser
{
    private String _password;
    private boolean _isEnabled;
    private boolean _doesTrustLoginLocationsAutomatically;
    private final Set<InetAddress> _trustedLoginLocations;
    private final Set<InetAddress> _distrustedLoginLocations;

    public ThirparauthUser(String password)
    {
        setPassword(password);
        _trustedLoginLocations = new HashSet<>();
        _distrustedLoginLocations = new HashSet<>();
        _doesTrustLoginLocationsAutomatically = true;
        _isEnabled = true;
    }

    public void setPassword(String newPassword)
    {
        Preconditions.checkArgument(newPassword != null, "newPassword must not be null");
        Preconditions.checkArgument(newPassword.isBlank() == false, "newPassword must not be blank");
        _password = newPassword;
    }

    public boolean verifies(String password)
    {
        return _password.equals(password);
    }

    public boolean isEnabled()
    {
        return _isEnabled;
    }

    public void setEnabled(boolean flag)
    {
        _isEnabled = flag;
    }

    public boolean doesTrustLoginLocationsAutomatically()
    {
        return _doesTrustLoginLocationsAutomatically;
    }

    public void trustLoginLocationsAutomatically(boolean flag)
    {
        _doesTrustLoginLocationsAutomatically = flag;
    }

    public boolean doesTrust(InetAddress loginLocation)
    {
        return _trustedLoginLocations.contains(loginLocation);
    }

    public void trust(InetAddress loginLocation)
    {
        if (_distrustedLoginLocations.contains(loginLocation))
        {
            _distrustedLoginLocations.remove(loginLocation);
        }

        _trustedLoginLocations.add(loginLocation);
    }

    public boolean doesDistrust(InetAddress loginLocation)
    {
        return _distrustedLoginLocations.contains(loginLocation);
    }

    public void distrust(InetAddress loginLocation)
    {
        if (_trustedLoginLocations.contains(loginLocation))
        {
            _trustedLoginLocations.remove(loginLocation);
        }

        _distrustedLoginLocations.add(loginLocation);
    }

    public void forget(InetAddress loginLocation)
    {
        if (_trustedLoginLocations.contains(loginLocation))
        {
            _trustedLoginLocations.remove(loginLocation);
        }
        
        if (_distrustedLoginLocations.contains(loginLocation))
        {
            _distrustedLoginLocations.remove(loginLocation);
        }
    }
}
