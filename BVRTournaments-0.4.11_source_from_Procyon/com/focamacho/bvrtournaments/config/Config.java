// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.config;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.annotation.Comment;

public class Config
{
    @Comment("General configs.")
    public General general;
    @Comment("Commands configs.")
    public Commands command;
    @Comment("Battles configs.")
    public Battles battles;
    @Comment("Bet configs.")
    public Bets bets;
    @Comment("MySQL configs.")
    public MySQL mysql;
    
    public Config() {
        this.general = new General();
        this.command = new Commands();
        this.battles = new Battles();
        this.bets = new Bets();
        this.mysql = new MySQL();
    }
    
    public static class General
    {
        @Comment("Default amount of points that a player starts with.")
        public int defaultPoints;
        @Comment("Send a message to the player when their points get changed.")
        public boolean pointsWarnMessage;
        @Comment("List of Elos. Format: (\"luckPermsGroupName\": amountOfPointsRequired)")
        public Elo[] elos;
        @Comment("Formula to calculate how much points the winner will get from the /updatepoints command.\nUse 'x' to represent the points of the player, and 'y' to represent the points of the opposing player.")
        public String updateWinFormula;
        @Comment("Formula to calculate how much points the loser will lost from the /updatepoints command.\nUse 'x' to represent the points of the player, and 'y' to represent the points of the opposing player.")
        public String updateDefeatFormula;
        @Comment("Use a inventory menu to show all pokemons from a tier.")
        public boolean tierInventory;
        @Comment("Broadcast that a Tournament was created.")
        public boolean broadcastTournament;
        @Comment("Max players limit for Tournaments.")
        public int maxPlayersTournament;
        @Comment("Price to join a tournament.")
        public double priceToJoinTournament;
        @Comment("Time, in minutes, between updates of the top points menu. Requires restart after changing.")
        public int topPointsUpdate;
        
        public General() {
            this.defaultPoints = 1000;
            this.pointsWarnMessage = true;
            this.elos = new Elo[] { new Elo("elo1", 50), new Elo("elo2", 70), new Elo("elo3", 100), new Elo("elo4", 150), new Elo("elo5", 270) };
            this.updateWinFormula = "x + 16 * (1 - (1 / (1 + 10 ^ ((y - x) / 400))))";
            this.updateDefeatFormula = "x + 16 * (0 - (1 / (1 + 10 ^ ((y - x) / 400))))";
            this.tierInventory = true;
            this.broadcastTournament = true;
            this.maxPlayersTournament = 16;
            this.priceToJoinTournament = 0.0;
            this.topPointsUpdate = 10;
        }
        
        public static class Elo
        {
            public String group;
            public int points;
            
            public Elo() {
            }
            
            public Elo(final String group, final int points) {
                this.group = group;
                this.points = points;
            }
        }
    }
    
    public static class Commands
    {
        @Comment("Command to check how many points the player have.")
        public CommandObject pointsCommand;
        @Comment("Command to add more points to a player.")
        public CommandObject addPointsCommand;
        @Comment("Command to remove points from a player.")
        public CommandObject removePointsCommand;
        @Comment("Command to set points for a player.")
        public CommandObject setPointsCommand;
        @Comment("Command to open the top points menu.")
        public CommandObject topPointsCommand;
        @Comment("Command to reset the entire points database.")
        public CommandObject resetPointsCommand;
        @Comment("Command to, based on a formula, add and remove points from two players (winner and loser).")
        public CommandObject updatePointsCommand;
        @Comment("Command to start a ranked battle between two players.")
        public CommandObject ilCommand;
        @Comment("Command to start a ranked battle between four players (2vs2).")
        public CommandObject ilDuoCommand;
        @Comment("Command to set yourself as 'ready' to start the battle.")
        public CommandObject readyCommand;
        @Comment("Command to see your opponents team.")
        public CommandObject teamPreviewCommand;
        @Comment("Command to make a bet.")
        public CommandObject betCommand;
        @Comment("Command to list all created arenas.")
        public CommandObject arenasCommand;
        @Comment("Command to create the arena location for the battles.")
        public CommandObject createArenaCommand;
        @Comment("Command to show the tiers of a pokemon.")
        public CommandObject tierCommand;
        @Comment("Main command for tournament commands.")
        public CommandObject tournamentCommand;
        @Comment("Sub-Command to join the current tournament.")
        public CommandObject tournamentJoinSubCommand;
        @Comment("Sub-Command to leave the current tournament.")
        public CommandObject tournamentLeaveSubCommand;
        @Comment("Sub-Command to cancel the current tournament.")
        public CommandObject tournamentCancelSubCommand;
        @Comment("Sub-Command to create a tournament.")
        public CommandObject tournamentCreateSubCommand;
        @Comment("Sub-Command to start a tournament.")
        public CommandObject tournamentStartSubCommand;
        @Comment("Sub-Command to finish the current tournament.")
        public CommandObject tournamentFinishSubCommand;
        @Comment("Sub-Command to kick a player from the current tournament.")
        public CommandObject tournamentKickSubCommand;
        @Comment("Sub-Command to see all the participants of the current tournament.")
        public CommandObject tournamentParticipantsSubCommand;
        
        public Commands() {
            this.pointsCommand = new CommandObject("Mostra quantos pontos o jogador possui.", "bvrtournaments.command.points", new String[] { "elo", "points" });
            this.addPointsCommand = new CommandObject("Adiciona pontos para um jogador.", "bvrtournaments.admin", new String[] { "addpoints" });
            this.removePointsCommand = new CommandObject("Remove pontos de um jogador.", "bvrtournaments.admin", new String[] { "removepoints" });
            this.setPointsCommand = new CommandObject("Define os pontos de um jogador.", "bvrtournaments.admin", new String[] { "setpoints" });
            this.topPointsCommand = new CommandObject("Mostra os jogadores no Rank de pontos.", "bvrtournaments.command.top", new String[] { "toppoints" });
            this.resetPointsCommand = new CommandObject("Reseta os pontos de todos os jogadores.", "bvrtournaments.admin", new String[] { "resetpoints" });
            this.updatePointsCommand = new CommandObject("Adiciona e remove pontos de dois jogadores inseridos baseado em uma f\u00f3rmula.", "bvrtournaments.admin", new String[] { "updatepoints" });
            this.ilCommand = new CommandObject("Teleporta e inicia uma luta entre dois jogadores.", "bvrtournaments.admin", new String[] { "il" });
            this.ilDuoCommand = new CommandObject("Teleporta e inicia uma luta entre quatro jogadores (2vs2).", "bvrtournaments.admin", new String[] { "ilduo" });
            this.readyCommand = new CommandObject("Define que voc\u00ea est\u00e1 pronto para a luta.", "", new String[] { "pronto" });
            this.teamPreviewCommand = new CommandObject("Visualiza os pok\u00e9mons de seu oponente.", "", new String[] { "teampreview" });
            this.betCommand = new CommandObject("Efetua uma aposta para a batalha.", "", new String[] { "apostar" });
            this.arenasCommand = new CommandObject("Lista todas as arenas criadas.", "bvrtournaments.admin", new String[] { "viewil" });
            this.createArenaCommand = new CommandObject("Define os locais onde os jogadores ser\u00e3o teleportados para as batalhas.", "bvrtournaments.admin", new String[] { "setil" });
            this.tierCommand = new CommandObject("Mostra a quais tier o Pok\u00e9mon pertence, ou quais pok\u00e9mons est\u00e3o no tier informado.", "", new String[] { "tier" });
            this.tournamentCommand = new CommandObject("Comando principal para os torneios.", "", new String[] { "tournament", "torneio" });
            this.tournamentJoinSubCommand = new CommandObject("Entra no torneio atual.", "", new String[] { "join", "entrar" });
            this.tournamentLeaveSubCommand = new CommandObject("Sai do torneio atual.", "", new String[] { "leave", "sair" });
            this.tournamentCancelSubCommand = new CommandObject("Cancela o torneio atual.", "bvrtournaments.admin", new String[] { "cancel", "cancelar" });
            this.tournamentCreateSubCommand = new CommandObject("Cria um novo torneio.", "bvrtournaments.admin", new String[] { "create", "criar" });
            this.tournamentStartSubCommand = new CommandObject("Inicia o torneio.", "bvrtournaments.admin", new String[] { "start", "iniciar" });
            this.tournamentFinishSubCommand = new CommandObject("Finaliza o torneio atual.", "bvrtournaments.admin", new String[] { "finish", "finalizar" });
            this.tournamentKickSubCommand = new CommandObject("Expulsa um jogador do torneio atual.", "bvrtournaments.admin", new String[] { "kick", "expulsar" });
            this.tournamentParticipantsSubCommand = new CommandObject("Exibe os participantes do torneio atual.", "", new String[] { "participants", "participantes", "inscritos" });
        }
        
        public static class CommandObject
        {
            public String description;
            public String permission;
            public String[] aliases;
            
            public CommandObject(final String description, final String permission, final String... aliases) {
                this.description = "";
                this.permission = "";
                this.aliases = new String[] { "" };
                this.description = description;
                this.permission = permission;
                this.aliases = aliases;
            }
            
            public CommandObject() {
                this.description = "";
                this.permission = "";
                this.aliases = new String[] { "" };
            }
        }
    }
    
    public static class Battles
    {
        @Comment("Time in seconds the player have to type /ready.")
        public int readyMaxTime;
        @Comment("If a message should be sent to all players when a battle is started using the /il command.")
        public boolean broadcastFight;
        @Comment("If a message should be sent to all players when a player wins a /il battle.")
        public boolean broadcastWin;
        @Comment("If the player should be teleported back to his old location after the end of a /il battle.")
        public boolean teleportBackIl;
        @Comment("Commands to be run when a battle ends. Use {0} to represent the winner player, and {1} to represent the defeated player.")
        public String[] endCommands;
        @Comment("Commants to be run when a 2vs2 battle ends. Use {0} and {1} to represent the winner players, and use {2} and {3} to represent the defeated players.")
        public String[] endDuoCommands;
        @Comment("Max time, in seconds, to choose an action. Set to -1 to disable.")
        public int maxTimeIL;
        
        public Battles() {
            this.readyMaxTime = 60;
            this.broadcastFight = true;
            this.broadcastWin = false;
            this.teleportBackIl = false;
            this.endCommands = new String[] { "updatepoints {0} {1}" };
            this.endDuoCommands = new String[0];
            this.maxTimeIL = -1;
        }
    }
    
    public static class Bets
    {
        @Comment("Enable bets for /il battles.")
        public boolean betsEnabled;
        @Comment("Boss bar color for the bets. Available colors: BLUE, GREEN, PINK, PURPLE, RED, WHITE and YELLOW")
        public String bossBarColor;
        @Comment("Boss bar overlay for the bets. Available overlays: PROGRESS, NOTCHED_6, NOTCHED_10, NOTCHED_12 and NOTCHED_20")
        public String bossBarOverlay;
        @Comment("Time in seconds the players have to bet after the start of a battle.")
        public int secondsToBet;
        @Comment("Send a global message when a bet starts.")
        public boolean broadcastBetAvailable;
        @Comment("Percentage removed from the total bet made on the losing player (Tax).")
        public float taxPercentage;
        @Comment("Percentage of the tax to give to the winner.")
        public float winnerTaxPercentage;
        
        public Bets() {
            this.betsEnabled = true;
            this.bossBarColor = "WHITE";
            this.bossBarOverlay = "PROGRESS";
            this.secondsToBet = 60;
            this.broadcastBetAvailable = true;
            this.taxPercentage = 10.0f;
            this.winnerTaxPercentage = 50.0f;
        }
    }
    
    public static class MySQL
    {
        @Comment("MySQL connection configs.")
        public String mysqlTablePrefix;
        public String mysqlTablePrefixArenas;
        public String mysqlAddress;
        public String mysqlDatabase;
        public String mysqlUser;
        public String mysqlPassword;
        @Comment("Advanced configs.")
        public Advanced advanced;
        
        public MySQL() {
            this.mysqlTablePrefix = "bvrtournaments_";
            this.mysqlTablePrefixArenas = "bvrtournaments_server_";
            this.mysqlAddress = "localhost:3306";
            this.mysqlDatabase = "bvrtournaments";
            this.mysqlUser = "bvrtournaments";
            this.mysqlPassword = "";
            this.advanced = new Advanced();
        }
        
        public static class Advanced
        {
            @Comment("Custom MySQL connection url.\nThis value will override the IP address defined in 'mysqlAddress' config.\nOnly change this value if you know what you are doing.")
            public String mysqlConnectionUrl;
            
            public Advanced() {
                this.mysqlConnectionUrl = "";
            }
        }
    }
}
