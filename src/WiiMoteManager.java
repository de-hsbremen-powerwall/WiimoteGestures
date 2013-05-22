import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

public class WiiMoteManager{
	
	private Player[] players;
	private Wiimote wiimotes[];
	private boolean[] pointerModeStatus;
	
	public WiiMoteManager(int player_count, int screenWidth, int screenHeight, boolean sensorbar_position){
		
		try {
			// Load available WiiMotes and check them once	
			wiimotes = WiiUseApiManager.getWiimotes(player_count, true);
			for(int i = 0; i<player_count; i++)
				System.out.println("Found WiiMote with the ID " + wiimotes[i].getId());
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("No WiiMote found for player! Quitting...");
				WiiUseApiManager.shutdown();
				System.exit(0);
			}
		
		// Create player Array and then create player objects with assigned wiimotes
		players = new Player[player_count];
		pointerModeStatus = new boolean[player_count];
		for(int i = 0; i<player_count; i++){
			players[i] = new Player(wiimotes[i].getId());
		}
		
		// Configure all WiiMotes
		for(int i = 0; i<player_count; i++){
			wiimotes[i].addWiiMoteEventListeners(players[i]);
			wiimotes[i].activateIRTRacking();
			pointerModeStatus[i] = true;
			wiimotes[i].setScreenAspectRatio169();
			wiimotes[i].setVirtualResolution(screenWidth, screenHeight);
			
			if(sensorbar_position)
				wiimotes[i].setSensorBarAboveScreen();
			else
				wiimotes[i].setSensorBarBelowScreen();
		}
		
		System.out.println("WiiMoteManager initialized for " + player_count + " players with 16:9 aspect ratio (" + 
		screenWidth + "x" + screenHeight + " px) with sensorbar above screen = " + sensorbar_position + ". Current mode: Pointer");
	}
	
	public Player getPlayer(int player_number){
		return players[player_number];
	}
	
	public int getPlayerCount(){
		return players.length;
	}
	
	public void setPointerMode(int id){
			wiimotes[id].deactivateMotionSensing();
			wiimotes[id].activateIRTRacking();
			pointerModeStatus[id] = true;
	}
	
	public void setMotionMode(int id){
			wiimotes[id].deactivateIRTRacking();
			wiimotes[id].activateMotionSensing();
			pointerModeStatus[id] = false;
	}
	
	public boolean isPointerModeActive(int id){
		return pointerModeStatus[id];
	}
	
	public void activateRumble(int player_number){
		wiimotes[player_number].activateRumble();
	}
	
	public void deactivateRumble(int player_number){
		wiimotes[player_number].deactivateRumble();
	}

}
