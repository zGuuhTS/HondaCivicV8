// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.config;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.annotation.Comment;

public class Lang
{
    @Comment("General messages configs.")
    public Geral geral;
    @Comment("Commands messages configs.")
    public Commands commands;
    @Comment("Others messages configs.")
    public Others others;
    
    public Lang() {
        this.geral = new Geral();
        this.commands = new Commands();
        this.others = new Others();
    }
    
    public static class Geral
    {
        @Comment("Messages configs:\n\nFirst line: Title\nSecond line: Subtitle\nThird line: Action bar\nFourth line: Chat\n\nLeave a line empty to not use it.")
        public String[] receivedPointsMessage;
        public String[] removedPointsMessage;
        public String[] setPointsMessage;
        public String[] broadcastILMessage;
        public String[] broadcastDuoILMessage;
        public String[] battleMessage;
        public String[] battleDuoMessage;
        public String[] noTeamSelect;
        public String[] winMessage;
        public String[] loseMessage;
        public String[] broadcastWinMessage;
        public String[] broadcastDuoWinMessage;
        public String[] abnormalEndMessage;
        public String[] betStarted;
        public String[] betTimeEnded;
        public String[] betTimeDuoEnded;
        public String[] betWon;
        public String[] betDuoWon;
        public String[] betWinner;
        public String[] tournamentBroadcast;
        public String[] tournamentTierBroadcast;
        public String[] pokemonSelection;
        public String[] challongeURLBroadcast;
        @Comment("{0} = Name of the first player.\n{1} = Name of the second player.\n%percentage1% = Percentage bet on the first player.\n%percentage2% = Percentage bet on the second player.\n%bet1% = Amount bet on the first player.\n%bet2% = Amount bet on the second player.\n%totalbet% = Total amount bet.")
        public String betBossBar;
        @Comment("{0} = Name of the first player.\n{1} = Name of the second player.\n%percentage1% = Percentage bet on the first player.\n%percentage2% = Percentage bet on the second player.\n%bet1% = Amount bet on the first player.\n%bet2% = Amount bet on the second player.\n%totalbet% = Total amount bet.")
        public String betDuoBossBar;
        
        public Geral() {
            this.receivedPointsMessage = new String[] { "", "", "&a+{0} ponto(s)", "&aVoc\u00ea recebeu {0} ponto(s)." };
            this.removedPointsMessage = new String[] { "", "", "&c-{0} ponto(s)", "&cVoc\u00ea perdeu {0} ponto(s)." };
            this.setPointsMessage = new String[] { "", "", "&a{0} ponto(s)", "&aVoc\u00ea agora possui {0} ponto(s)." };
            this.broadcastILMessage = new String[] { "", "", "", "&aUma batalha entre {0} e {1} j\u00e1 vai come\u00e7ar!" };
            this.broadcastDuoILMessage = new String[] { "", "", "", "&aUma batalha de {0} e {1} contra {2} e {3} j\u00e1 vai come\u00e7ar!" };
            this.battleMessage = new String[] { "&aBatalha", "&cAdvers\u00e1rio: &7{0}", "&7Digite &b/pronto &7para come\u00e7ar!", "&7Digite &b/pronto &7para come\u00e7ar a sua batalha!" };
            this.battleDuoMessage = new String[] { "&aBatalha", "&cAdvers\u00e1rios: &7{0} e {1}", "&7Digite &b/pronto &7para come\u00e7ar!", "&7Digite &b/pronto &7para come\u00e7ar a sua batalha!" };
            this.noTeamSelect = new String[] { "", "", "", "&7A Sele\u00e7\u00e3o de Times &cn\u00e3o &7est\u00e1 habilitada neste modo, use o pok\u00e9mon priorit\u00e1rio no primeiro slot da sua party." };
            this.winMessage = new String[] { "&aVit\u00f3ria!", "&7Parab\u00e9ns pela vit\u00f3ria!", "", "&aVoc\u00ea ganhou a batalha." };
            this.loseMessage = new String[] { "&cDerrota!", "&7N\u00e3o foi dessa vez.", "", "&cVoc\u00ea perdeu a batalha." };
            this.broadcastWinMessage = new String[] { "", "", "", "&a{0} ganhou a batalha!" };
            this.broadcastDuoWinMessage = new String[] { "", "", "", "&a{0} e {1} ganharam a batalha!" };
            this.abnormalEndMessage = new String[] { "&cBatalha Encerrada!", "&7A batalha foi encerrada.", "", "" };
            this.betStarted = new String[] { "", "", "", "&aUse &b/apostar &apara apostar em quem vai ganhar!" };
            this.betTimeEnded = new String[] { "", "", "", "&aApostas encerradas. Caso {0} ven\u00e7a voc\u00ea receber\u00e1: {1}$" };
            this.betTimeDuoEnded = new String[] { "", "", "", "&aApostas encerradas. Caso {0} e {1} ven\u00e7am voc\u00ea receber\u00e1: {2}$" };
            this.betWon = new String[] { "", "", "", "&a{0} venceu! Voc\u00ea recebeu {1}$" };
            this.betDuoWon = new String[] { "", "", "", "&a{0} e {1} venceram! Voc\u00ea recebeu {1}$" };
            this.betWinner = new String[] { "", "", "", "&aParab\u00e9ns {0}! Voc\u00ea recebeu {1}$ das apostas feitas!" };
            this.tournamentBroadcast = new String[] { "", "", "", " \n&eUm torneio pok\u00e9mon foi iniciado!\n&eUtilize &b/torneio entrar &epara participar!\n " };
            this.tournamentTierBroadcast = new String[] { "", "", "", " \n&eUm torneio de &dTier {0} &efoi iniciado!\n&eUtilize &b/torneio entrar &epara participar!\n " };
            this.pokemonSelection = new String[] { "", "", "", "&eEscolha, em ordem de batalha, todos os 6 pok\u00e9mons que ser\u00e3o usados!" };
            this.challongeURLBroadcast = new String[] { "", "", "", " \n&eO torneio pode ser acompanhado em:\n&b{0}\n " };
            this.betBossBar = "{0} - %bet1%$ (%percentage1%%) | (%percentage2%%) %bet2%$ - {1}";
            this.betDuoBossBar = "{0} e {1} - %bet1%$ (%percentage1%%) | (%percentage2%%) %bet2%$ - {2} e {3}";
        }
    }
    
    public static class Commands
    {
        @Comment("Geral")
        public String notPlayer;
        public String playerNotFound;
        @Comment("Points")
        public String pointsMessage;
        public String pointsNoEloMessage;
        public String pointsOtherMessage;
        public String pointsOtherNoEloMessage;
        public String invalidValue;
        public String pointsAdd;
        public String pointsRemove;
        public String pointsSet;
        public String resetWarning;
        public String resetConfirmed;
        public String pointsUpdateWin;
        public String pointsUpdateDefeat;
        @Comment("Battles")
        public String notInBattle;
        public String ready;
        public String alreadyReady;
        public String samePlayer;
        public String missingArena;
        public String posSet;
        public String clickToSet;
        public String typeToCancel;
        public String alreadyCreating;
        public String canceled;
        public String createdArena;
        public String arenasList;
        public String arenaItem;
        public String duoArenaItem;
        public String coordinates;
        public String hoverCoordinates;
        public String duoHoverCoordinates;
        public String remove;
        public String clickToRemove;
        public String noArenas;
        public String clickNextPage;
        public String clickPreviousPage;
        public String arenasList2;
        public String teamPreview;
        public String teamPreviewNotInBattle;
        public String teamPreviewNotInBattleOther;
        public String maxPokes;
        @Comment("Bets")
        public String alreadyBet;
        public String betMade;
        public String totalBet;
        public String cantBet;
        public String betCanceled;
        public String missingMoney;
        public String invalidPlayer;
        @Comment("Tiers")
        public String notFoundTier;
        public String pokemonTiers;
        public String pokemonNoTier;
        public String tierPokemonList;
        public String invalidTierForBattle;
        public String missingPoke;
        public String invalidTier;
        @Comment("Tournament")
        public String tournamentMessage;
        public String tournamentNotAvailable;
        public String tournamentStarted;
        public String tournamentMaxPlayer;
        public String tournamentNotIn;
        public String tournamentAlreadyIn;
        public String tournamentJoin;
        public String tournamentLeave;
        public String tournamentCancel;
        public String tournamentCancelNotAvailable;
        public String tournamentStartNotAvailable;
        public String tournamentStartAlreadyStarted;
        public String tournamentStart;
        public String invalidTierForTournament;
        public String tournamentAlreadyRunning;
        public String tournamentCreate;
        public String tournamentRequiredPlayers;
        public String playerJoin;
        public String playerLeave;
        public String tournamentFinishNotAvailable;
        public String tournamentFinish;
        public String tournamentPlayerNotIn;
        public String tournamentKick;
        public String tournamentParticipantsNotAvailable;
        public String tournamentParticipants;
        public String noMoney;
        public String removedMoney;
        public String addMoney;
        @Comment("Challonge")
        public String noKey;
        public String invalidKey;
        public String generatingTournament;
        public String errorGeneratingTournament;
        public String challongeUrl;
        
        public Commands() {
            this.notPlayer = "&cEi, s\u00f3 jogadores podem fazer isso.";
            this.playerNotFound = "&cO jogador mencionado n\u00e3o foi encontrado.";
            this.pointsMessage = "&eVoc\u00ea possui &b{0} ponto(s) &ee seu elo \u00e9 &b{1}&e!";
            this.pointsNoEloMessage = "&eVoc\u00ea possui &b{0} ponto(s) &ee n\u00e3o possui um elo ainda!";
            this.pointsOtherMessage = "&eO jogador &b{0} &epossui &b{1} ponto(s) &ee seu elo \u00e9 &b{2}&e!";
            this.pointsOtherNoEloMessage = "&eO jogador {0} possui &b{1} ponto(s) &ee n\u00e3o possui um elo ainda!";
            this.invalidValue = "&cO valor inserido n\u00e3o \u00e9 v\u00e1lido.";
            this.pointsAdd = "&aForam adicionados &b{0} ponto(s) &apara o jogador.";
            this.pointsRemove = "&aForam removidos &b{0} ponto(s) &ado o jogador.";
            this.pointsSet = "&aOs pontos do jogador foram definidos para &b{0} ponto(s)&a.";
            this.resetWarning = "&c&lESSE COMANDO APAGAR\u00c1 TODOS OS JOGADORES DO BANCO DE DADOS! DIGITE O COMANDO NOVAMENTE SE VOC\u00ca TEM CERTEZA DISSO!";
            this.resetConfirmed = "&aTodos os jogadores foram apagados do banco de dados.";
            this.pointsUpdateWin = "&aO jogador &b{0} &ateve seus pontos definidos para &b{1} &a(&2+{2}&a).";
            this.pointsUpdateDefeat = "&aO jogador &b{0} &ateve seus pontos definidos para &b{1} &a(&c-{2}&a).";
            this.notInBattle = "&cVoc\u00ea n\u00e3o est\u00e1 participando de uma batalha.";
            this.ready = "&aVoc\u00ea est\u00e1 pronto!";
            this.alreadyReady = "&cVoc\u00ea j\u00e1 confirmou que est\u00e1 pronto!";
            this.samePlayer = "&cUm jogador n\u00e3o pode batalhar com ele mesmo.";
            this.missingArena = "&cN\u00e3o h\u00e1 arenas dispon\u00edveis para a batalha.";
            this.posSet = "&aA posi\u00e7\u00e3o foi definida.";
            this.clickToSet = "&eClique em um bloco para definir a posi\u00e7\u00e3o &b{0} &edo time &b{1}&e.";
            this.typeToCancel = "&eDigite &c/cancelar &epara cancelar a cria\u00e7\u00e3o.";
            this.alreadyCreating = "&cVoc\u00ea j\u00e1 est\u00e1 criando uma arena. Digita /cancelar para cancelar a cria\u00e7\u00e3o.";
            this.canceled = "&aA cria\u00e7\u00e3o da arena foi cancelada.";
            this.createdArena = "&aA arena foi criada!";
            this.arenasList = "&e&l=-=-=-=-=-=-= Arenas {0}/{1} &e&l=-=-=-=-=-=-=";
            this.arenaItem = "&aArena {0} &7({1}&7) {2}";
            this.duoArenaItem = "&aArena {0} &d(Duo) &7({1}&7) {2}";
            this.coordinates = "&7x = {0}, z = {1}";
            this.hoverCoordinates = "&e1. &7{0}\n&e2. &7{1}\n\n&b&lClique para se teleportar at\u00e9 o local.";
            this.duoHoverCoordinates = "&e1. &7{0}\n&e2. &7{1}\n&e3. &7{2}\n&e4. &7{3}\n\n&b&lClique para se teleportar at\u00e9 o local.";
            this.remove = "&c[Remover]";
            this.clickToRemove = "&c&oClique para remover essa arena.";
            this.noArenas = "&cNenhuma arena foi definida.";
            this.clickNextPage = "&a&oClique para ir para a pr\u00f3xima p\u00e1gina.";
            this.clickPreviousPage = "&a&oClique para ir para a p\u00e1gina anterior.";
            this.arenasList2 = "&e&l=-={0}&e&l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-{1}&e&l=-=";
            this.teamPreview = "&7O jogador &b{0} &7possui os seguintes pok\u00e9mons: &d{1}&7.";
            this.teamPreviewNotInBattle = "&cVoc\u00ea s\u00f3 pode usar esse comando quando for batalhar.";
            this.teamPreviewNotInBattleOther = "&cVoc\u00ea s\u00f3 pode usar esse comando nos participantes da batalha.";
            this.maxPokes = "&cO jogador &b{0} &cn\u00e3o possui a quantia certa de pok\u00e9mons.";
            this.alreadyBet = "&cVoc\u00ea j\u00e1 fez uma aposta em outro jogador.";
            this.betMade = "&aVoc\u00ea apostou {0}$ em {1}.";
            this.totalBet = "&aSua aposta total \u00e9 de: {0}$";
            this.cantBet = "&cVoc\u00ea n\u00e3o pode apostar agora.";
            this.betCanceled = "&cA aposta foi cancelada. Voc\u00ea recebeu seu dinheiro de volta.";
            this.missingMoney = "&cVoc\u00ea n\u00e3o tem dinheiro suficiente para apostar.";
            this.invalidPlayer = "&cEsse jogador n\u00e3o est\u00e1 fazendo parte de uma aposta.";
            this.notFoundTier = "&cN\u00e3o foi poss\u00edvel encontrar a Tier ou Pok\u00e9mon inserido.";
            this.pokemonTiers = "&b{0} &apode ser usado nas tiers: &b{1}";
            this.pokemonNoTier = "&cEsse pok\u00e9mon n\u00e3o est\u00e1 em nenhuma tier.";
            this.tierPokemonList = "&aA tier &b{0} &apossui os seguintes pok\u00e9mons: &b{1}";
            this.invalidTierForBattle = "&cO jogador &b{0} &cpossui pok\u00e9mons que n\u00e3o s\u00e3o do tier especificado.";
            this.missingPoke = "&cO jogador &b{0} &cn\u00e3o possui um pok\u00e9mon v\u00e1lido para a luta.";
            this.invalidTier = "&cO tier inserido n\u00e3o existe.";
            this.tournamentMessage = "&c/torneio entrar|sair";
            this.tournamentNotAvailable = "&cN\u00e3o h\u00e1 nenhum torneio para entrar.";
            this.tournamentStarted = "&cO torneio atual j\u00e1 come\u00e7ou!";
            this.tournamentMaxPlayer = "&cO torneio est\u00e1 cheio.";
            this.tournamentNotIn = "&cVoc\u00ea n\u00e3o est\u00e1 em um torneio.";
            this.tournamentAlreadyIn = "&cVoc\u00ea j\u00e1 est\u00e1 no torneio.";
            this.tournamentJoin = "&aVoc\u00ea entrou no torneio.";
            this.tournamentLeave = "&aVoc\u00ea saiu do torneio.";
            this.tournamentCancel = "&aO torneio foi cancelado.";
            this.tournamentCancelNotAvailable = "&cN\u00e3o h\u00e1 nenhum torneio para cancelar.";
            this.tournamentStartNotAvailable = "&cN\u00e3o h\u00e1 nenhum torneio para iniciar.";
            this.tournamentStartAlreadyStarted = "&cO torneio j\u00e1 foi iniciado.";
            this.tournamentStart = "&aO torneio foi iniciado.";
            this.invalidTierForTournament = "&cVoc\u00ea n\u00e3o pode entrar no torneio pois os seguintes Pok\u00e9mons n\u00e3o se encaixam no &4Tier {0}&c: &d{1}";
            this.tournamentAlreadyRunning = "&cJ\u00e1 existe um torneio acontecendo no momento.";
            this.tournamentCreate = "&aO torneio foi criado com sucesso.";
            this.tournamentRequiredPlayers = "&c\u00c9 necess\u00e1rio que no m\u00ednimo 2 pessoas estejam participando do torneio para inici\u00e1-lo.";
            this.playerJoin = "&b{0} &eentrou no torneio.";
            this.playerLeave = "&b{0} &csaiu do torneio.";
            this.tournamentFinishNotAvailable = "&cN\u00e3o h\u00e1 nenhum torneio para finalizar.";
            this.tournamentFinish = "&aO torneio foi finalizado.";
            this.tournamentPlayerNotIn = "&cO jogador n\u00e3o est\u00e1 participando do torneio.";
            this.tournamentKick = "&aO jogador foi removido do torneio.";
            this.tournamentParticipantsNotAvailable = "&cN\u00e3o h\u00e1 nenhum torneio em execu\u00e7\u00e3o no momento.";
            this.tournamentParticipants = "&aParticipantes: &e{0}\n&aTotal: &b{1}";
            this.noMoney = "&cVoc\u00ea n\u00e3o possui dinheiro suficiente.\n\u00c9 necess\u00e1rio ${0} para entrar no torneio.";
            this.removedMoney = "&c${0} &aforam removidos de sua conta.";
            this.addMoney = "&a${0} foram adicionados a sua conta.";
            this.noKey = "&cNenhuma key para o Challonge foi inserida no arquivo de configura\u00e7\u00e3o.";
            this.invalidKey = "&cA chave inserida para o Challonge \u00e9 inv\u00e1lida.";
            this.generatingTournament = "&aCriando torneio no Challonge...";
            this.errorGeneratingTournament = "&cOcorreu um erro e n\u00e3o foi poss\u00edvel gerar o torneio.";
            this.challongeUrl = "&aLink do torneio: {0}";
        }
    }
    
    public static class Others
    {
        public String missingElo;
        public String missingRank;
        
        public Others() {
            this.missingElo = "Nenhum";
            this.missingRank = "?";
        }
    }
}
