package app.ichingoracle.core;

import javafx.scene.Node;


public interface MethodPlugin
{
    String getId();
    String getVersion();
    String getName();
    String getHash();

    Node getMethod();
    Node getResult();

    void register(MethodPluginCallback subscriber);
}
