// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.compat;

import me.rojo8399.placeholderapi.ExpansionBuilder;
import me.rojo8399.placeholderapi.Placeholder;
import me.rojo8399.placeholderapi.Source;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.ProvisioningException;
import com.focamacho.bvrtournaments.BVRTournaments;

public class PlaceholderAPICompat
{
    private final BVRTournaments plugin;
    
    public PlaceholderAPICompat(final BVRTournaments plugin) throws ProvisioningException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   java/lang/Object.<init>:()V
        //     4: aload_0         /* this */
        //     5: aload_1         /* plugin */
        //     6: putfield        com/focamacho/bvrtournaments/compat/PlaceholderAPICompat.plugin:Lcom/focamacho/bvrtournaments/BVRTournaments;
        //     9: invokestatic    org/spongepowered/api/Sponge.getServiceManager:()Lorg/spongepowered/api/service/ServiceManager;
        //    12: ldc             Lme/rojo8399/placeholderapi/PlaceholderService;.class
        //    14: invokeinterface org/spongepowered/api/service/ServiceManager.provideUnchecked:(Ljava/lang/Class;)Ljava/lang/Object;
        //    19: checkcast       Lme/rojo8399/placeholderapi/PlaceholderService;
        //    22: astore_2        /* placeholder */
        //    23: aload_2         /* placeholder */
        //    24: aload_0         /* this */
        //    25: aload_1         /* plugin */
        //    26: invokeinterface me/rojo8399/placeholderapi/PlaceholderService.loadAll:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;
        //    31: invokeinterface java/util/List.stream:()Ljava/util/stream/Stream;
        //    36: invokedynamic   BootstrapMethod #0, apply:()Ljava/util/function/Function;
        //    41: invokeinterface java/util/stream/Stream.map:(Ljava/util/function/Function;)Ljava/util/stream/Stream;
        //    46: invokedynamic   BootstrapMethod #1, apply:()Ljava/util/function/Function;
        //    51: invokeinterface java/util/stream/Stream.map:(Ljava/util/function/Function;)Ljava/util/stream/Stream;
        //    56: invokedynamic   BootstrapMethod #2, accept:()Ljava/util/function/Consumer;
        //    61: invokeinterface java/util/stream/Stream.forEach:(Ljava/util/function/Consumer;)V
        //    66: return         
        //    Exceptions:
        //  throws org.spongepowered.api.service.ProvisioningException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Placeholder(id = "rnk-elo")
    public String getElo(@Source final Player player) {
        return this.plugin.getConfigController().getEloName(player.getName());
    }
    
    @Placeholder(id = "rnk-points")
    public String getPoints(@Source final Player player) {
        return this.plugin.getDatabaseController().getCachedUser(player.getName()).getPoints().toString();
    }
}
