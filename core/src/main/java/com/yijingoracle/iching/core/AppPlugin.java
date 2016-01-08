package com.yijingoracle.iching.core;

import javafx.scene.Node;


public interface AppPlugin
{
    String getName();

    Node getMethod();

    void register(AppPluginCallback subscriber);
}
