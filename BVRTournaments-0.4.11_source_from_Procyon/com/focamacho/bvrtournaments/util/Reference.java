// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.util;

import java.io.File;

public class Reference
{
    public static final File CONFIG_FOLDER;
    public static final File CONFIG_FILE;
    public static final File MENU_FILE;
    public static final File LANG_FILE;
    public static final File CHALLONGE_FILE;
    public static final File TIERS_FOLDER;
    public static final File SQLITE_FILE;
    
    static {
        CONFIG_FOLDER = new File("./config/BVRTournaments/");
        CONFIG_FILE = new File(Reference.CONFIG_FOLDER, "config.json");
        MENU_FILE = new File(Reference.CONFIG_FOLDER, "menus.json");
        LANG_FILE = new File(Reference.CONFIG_FOLDER, "lang.json");
        CHALLONGE_FILE = new File(Reference.CONFIG_FOLDER, "challonge.json");
        TIERS_FOLDER = new File(Reference.CONFIG_FOLDER, "tiers/");
        SQLITE_FILE = new File(Reference.CONFIG_FOLDER, "db_bvrtournaments.db");
    }
}
