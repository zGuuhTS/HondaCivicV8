// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.tournament;

import net.minecraft.entity.player.EntityPlayerMP;
import java.util.Map;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.net.HttpURLConnection;
import com.focamacho.bvrtournaments.config.Challonge;
import java.net.MalformedURLException;
import java.io.IOException;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.TextActions;
import java.net.URL;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.Sponge;
import java.util.Arrays;
import java.io.Reader;
import java.io.InputStreamReader;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import java.text.MessageFormat;
import com.google.common.collect.ArrayListMultimap;
import org.spongepowered.api.scheduler.Task;
import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import java.util.Optional;
import com.focamacho.bvrtournaments.util.TextUtils;
import com.focamacho.bvrtournaments.controller.TournamentController;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class StartTournamentCommand extends Command
{
    private static final String challongeURL = "https://api.challonge.com/v1/";
    
    public StartTournamentCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        final Optional<TournamentController.Tournament> optTournament = this.plugin.getTournamentController().getCurrentTournament();
        if (!optTournament.isPresent() || optTournament.get().isEnded()) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentStartNotAvailable, new Object[0]);
            return CommandResult.success();
        }
        if (optTournament.get().isStarted()) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentStartAlreadyStarted, new Object[0]);
            this.generateChallonge(src, optTournament.get());
            return CommandResult.success();
        }
        if (optTournament.get().getParticipants().size() > 1) {
            optTournament.get().start();
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentStart, new Object[0]);
            this.generateChallonge(src, optTournament.get());
        }
        else {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentRequiredPlayers, new Object[0]);
        }
        return CommandResult.success();
    }
    
    private void generateChallonge(final CommandSource src, final TournamentController.Tournament tournament) {
        Challonge challongeConfig;
        HttpURLConnection createTournamentPost;
        ArrayListMultimap parameters;
        Gson gson;
        JsonObject response;
        int tournamentID;
        HttpURLConnection participantsPost;
        String url;
        final String[] array;
        int length;
        int i = 0;
        String s;
        final Iterator<Player> iterator;
        Player onlinePlayer;
        Task.builder().execute(() -> {
            if (tournament.getURL() == null) {
                challongeConfig = this.plugin.getChallongeConfig();
                TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.generatingTournament, new Object[0]);
                if (challongeConfig.challongeAPIKey.isEmpty()) {
                    TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.noKey, new Object[0]);
                    return;
                }
                else {
                    try {
                        createTournamentPost = this.createConnection("tournaments.json");
                        parameters = ArrayListMultimap.create();
                        parameters.put((Object)"api_key", (Object)challongeConfig.challongeAPIKey);
                        challongeConfig.parameters.forEach((key, value) -> parameters.put((Object)key, (Object)MessageFormat.format(value, (tournament.getTier() != null) ? tournament.getTier().tierName : "")));
                        this.setParameters(createTournamentPost, (ArrayListMultimap<String, String>)parameters);
                        if (createTournamentPost.getResponseCode() == 401) {
                            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.invalidKey, new Object[0]);
                            return;
                        }
                        else {
                            gson = new Gson();
                            response = gson.fromJson(new InputStreamReader(createTournamentPost.getInputStream()), JsonObject.class);
                            tournamentID = response.getAsJsonObject("tournament").get("id").getAsInt();
                            participantsPost = this.createConnection("tournaments/" + tournamentID + "/participants/bulk_add.json");
                            parameters.clear();
                            parameters.put((Object)"api_key", (Object)challongeConfig.challongeAPIKey);
                            tournament.getParticipants().forEach(participant -> parameters.put((Object)"participants[][name]", (Object)participant.func_70005_c_()));
                            this.setParameters(participantsPost, (ArrayListMultimap<String, String>)parameters);
                            participantsPost.getInputStream();
                            url = response.getAsJsonObject("tournament").get("full_challonge_url").getAsString();
                            tournament.setURL(url);
                            if (this.plugin.getChallongeConfig().broadcastChallonge) {
                                TextUtils.broadcastFormattedMessage(Arrays.copyOf(this.plugin.getLang().geral.challongeURLBroadcast, 3), tournament.getURL());
                                this.plugin.getLang().geral.challongeURLBroadcast[3].split("\n");
                                for (length = array.length; i < length; ++i) {
                                    s = array[i];
                                    Sponge.getServer().getOnlinePlayers().iterator();
                                    while (iterator.hasNext()) {
                                        onlinePlayer = iterator.next();
                                        onlinePlayer.sendMessage(TextUtils.formatText(s, tournament.getURL()).toBuilder().onClick((ClickAction)TextActions.openUrl(new URL(tournament.getURL()))).build());
                                    }
                                }
                            }
                        }
                    }
                    catch (IOException e) {
                        TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.errorGeneratingTournament, new Object[0]);
                        e.printStackTrace();
                        return;
                    }
                }
            }
            try {
                src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.challongeUrl, tournament.getURL()).toBuilder().onClick((ClickAction)TextActions.openUrl(new URL(tournament.getURL()))).build());
            }
            catch (MalformedURLException e2) {
                e2.printStackTrace();
            }
        }).async().submit((Object)this.plugin);
    }
    
    private HttpURLConnection createConnection(final String url) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection)new URL("https://api.challonge.com/v1/" + url).openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(10000);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }
    
    private void setParameters(final HttpURLConnection connection, final ArrayListMultimap<String, String> parameters) throws IOException {
        final OutputStream os = connection.getOutputStream();
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        final String params = String.join("&", (CharSequence[])parameters.entries().stream().map(entry -> {
            try {
                return URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode((String)entry.getValue(), "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }).toArray(String[]::new));
        writer.write(params);
        writer.flush();
        writer.close();
        os.close();
    }
}
