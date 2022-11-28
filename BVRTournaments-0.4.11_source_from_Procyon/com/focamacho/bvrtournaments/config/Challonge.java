// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.config;

import com.google.common.collect.Maps;
import java.util.Map;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.annotation.Comment;

public class Challonge
{
    @Comment("API Key for connecting to Challonge. Can be obtained in https://challonge.com/pt_BR/settings/developer.")
    public String challongeAPIKey;
    @Comment("Broadcsat URL when generated.")
    public boolean broadcastChallonge;
    @Comment("Request Parameters for when creating a new tournament.")
    public Map<String, String> parameters;
    
    public Challonge() {
        this.challongeAPIKey = "";
        this.broadcastChallonge = true;
        (this.parameters = (Map<String, String>)Maps.newHashMap()).put("tournament[name]", "{0}");
        this.parameters.put("tournament[hold_third_place_match]", "true");
    }
}
