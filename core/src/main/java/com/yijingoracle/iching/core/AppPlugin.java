package com.yijingoracle.iching.core;

import javafx.scene.Node;


public interface AppPlugin
{
    String getId();
    String getVersion();
    String getName();

    Node getMethod();
    Node getResult();

    void register(AppPluginCallback subscriber);
}
