package com.goiching.iching.plugin.plumblossom.universal;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Base64;


public class License
{
    public static String checkLicenseAndGetUserInfo(InputStream stream, String expectedHash)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder out = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null)
                out.append(line);

            String license = decrypt(out.toString());
            String hash = digest(license);

            if (!hash.equals(expectedHash))
                throw new RuntimeException("Invalid license file");

            String[] split = license.split(",");

            String info = split.length == 3 ? String.format("%s %s [%s]", split[0], split[1], split[2]) : split[0];

            return info;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static HBox getUserInfoContainer(String info)
    {
        Label license = new Label(info);
        license.setStyle("-fx-font-weight: bold;");

        HBox frame = new HBox();
        frame.getChildren().add(license);
        frame.setAlignment(Pos.CENTER_RIGHT);

        return frame;
    }

    private static String digest(String message)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(message.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();

            for (byte b : digest)
            {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        }
        catch (Exception e)
        {
        }

        return "";
    }

    private static String decrypt(String message)
    {
        try
        {
            byte[] binLicense = Base64.getDecoder().decode(message);

            Cipher rc4 = Cipher.getInstance("RC4");
            SecretKeySpec rc4Key = new SecretKeySpec(KEY.getBytes(), "RC4");
            rc4.init(Cipher.DECRYPT_MODE, rc4Key);
            String license = new String(rc4.update(binLicense));

            return license;
        }
        catch(Exception e)
        {
        }

        return "";
    }

    private static final String KEY = "ichingoracle";
}
