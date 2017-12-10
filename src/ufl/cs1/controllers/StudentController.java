package ufl.cs1.controllers;
import game.controllers.DefenderController;
import game.models.Attacker;
import game.models.Defender;
import game.models.Game;
import game.models.Node;

import java.util.List;

public final class StudentController implements DefenderController {
	public void init(Game game) {
		//ohohohohoho
	}

	public void shutdown(Game game) {
	}

	private void chargeStrategy(int[] actions, int i, Defender defender, Attacker attacker, List<Integer> possibleDirs) {
		// If the defender can narrow the gap between him and the attacker, move towards her across the farthest axis first.
		if ((Math.abs(attacker.getLocation().getX() - defender.getLocation().getX()) < Math.abs(attacker.getLocation().getY() - defender.getLocation().getY()))
				&& (((possibleDirs.indexOf(Game.Direction.UP)) != -1) || (possibleDirs.indexOf(Game.Direction.DOWN)) != -1)) {
			// When the vertical distance between the defender and the attacker is greater than the horizontal distance, move vertically.
			if (attacker.getLocation().getY() < defender.getLocation().getY() && possibleDirs.indexOf(Game.Direction.UP) != -1) {
				// Get attacker Y location.  If it is less than (higher up) than defender AND I can move up, then move up.
				actions[i] = Game.Direction.UP;
				return;
			}
			if (attacker.getLocation().getY() > defender.getLocation().getY() && possibleDirs.indexOf(Game.Direction.DOWN) != -1) {
				// Get attacker Y location.  If it is greater than (lower down) defender AND I can move down, then move down.
				actions[i] = Game.Direction.DOWN;
				return;
			}
		} else {
			if (attacker.getLocation().getX() < defender.getLocation().getX() && possibleDirs.indexOf(Game.Direction.LEFT) != -1) {
				// Get attacker X location.  If it is less than defender AND I can move to the left, then move to the left.
				actions[i] = Game.Direction.LEFT;
				return;
			}
			if (attacker.getLocation().getX() > defender.getLocation().getX() && possibleDirs.indexOf(Game.Direction.RIGHT) != -1) {
				// Get attacker X location.  If it is greater than defender AND I can move to the right, then move right.
				actions[i] = Game.Direction.RIGHT;
				return;
			}
		}
		// If ideal direction is not available, pick the next best direction.
		if (attacker.getLocation().getX() < defender.getLocation().getX() && possibleDirs.indexOf(Game.Direction.LEFT) != -1) {
			// Get attacker X location.  If it is less than defender AND I can move to the left, then move to the left.
			actions[i] = Game.Direction.LEFT;
		} else if (attacker.getLocation().getX() > defender.getLocation().getX() && possibleDirs.indexOf(Game.Direction.RIGHT) != -1) {
			// Get attacker X location.  If it is greater than defender AND I can move to the right, then move to the right.
			actions[i] = Game.Direction.RIGHT;
		} else if (attacker.getLocation().getY() < defender.getLocation().getY() && possibleDirs.indexOf(Game.Direction.UP) != -1) {
			// Get attacker Y location.  If it is less than (higher up) than defender AND I can move up, then move up.
			actions[i] = Game.Direction.UP;
		} else if (attacker.getLocation().getY() > defender.getLocation().getY() && possibleDirs.indexOf(Game.Direction.DOWN) != -1) {
			// Get attacker Y location.  If it is greater than (lower down) defender AND I can move down, then move down.
			actions[i] = Game.Direction.DOWN;
		}
	}

	public int[] update(Game game, long timeDue) {
		int[] actions = new int[Game.NUM_DEFENDER];
		List<Defender> enemies = game.getDefenders();

		//Starts the loop for each ghost action
		for (int i = 0; i < actions.length; i++) {
			Defender defender = enemies.get(i);
			List<Integer> possibleDirs = defender.getPossibleDirs();
			if (i == 4) { //Madelyn's Code
				Attacker attacker = game.getAttacker();

					chargeStrategy(actions, i, defender, attacker, possibleDirs);

					if (defender.isVulnerable()) {

						if (actions[i] == Game.Direction.UP) {
							actions[i] = Game.Direction.DOWN;
						} else if (actions[i] == Game.Direction.DOWN) {
							actions[i] = Game.Direction.UP;
						} else if (actions[i] == Game.Direction.LEFT) {
							actions[i] = Game.Direction.RIGHT;
						} else if (actions[i] == Game.Direction.RIGHT) {
							actions[i] = Game.Direction.LEFT;
						}
					} /*else {
					if (possibleDirs.size() != 0)
						actions[i] = possibleDirs.get(Game.rng.nextInt(possibleDirs.size()));
					else
						actions[i] = -1;
				}*/
				}


				if (i == 3) {//Nic's code
					List<Node> powerPills = game.getPowerPillList();
					Attacker player = game.getAttacker();

					int countSize = powerPills.size(), powerPillContact = 0;
					int counter = 0;
					Node attackPosition = player.getLocation();

					if (countSize == 0) {
						actions[i] = defender.getNextDir(attackPosition, true);
					} else {
						if (counter >= powerPills.size()) {
							countSize = powerPills.size();
							counter++;
						}
						if (defender.getLocation().getPathDistance(powerPills.get(counter)) < 50) {
							countSize = powerPills.size();
							counter++;

							if (!(counter >= powerPills.size())) {
								if (powerPills.get(counter) == null && powerPills.get(counter) == defender.getLocation())
									powerPillContact++;

								if (powerPillContact == 3)
									powerPillContact = 0;

								break;
							}
						}
						if (counter >= countSize)
							counter = 0;

						actions[i] = defender.getNextDir(powerPills.get(counter), true);

					}
				}

			if(i == 2) //Preethi's code
			{
				List<Node> powerPills = game.getPowerPillList();
				Attacker pacman = game.getAttacker();
				Node attackPosition = pacman.getLocation();

				Node defPosition = defender.getLocation(); //gets the location of the defender position
				Node nearestPill = null; //sets the pill to null


				int x = attackPosition.getX(); //sets x to the x-axis of the attacker
				int y = attackPosition.getY(); //sets y to the y-axis of the defender

				int xOfD = defPosition.getX(); //sets x to the x-axis of the defender
				int yOfD = defPosition.getY(); //sets y to the y-axis of the defender

				int diffX = xOfD - x; //gets the x difference between defender and attacker
				int diffY = yOfD - y; //gets the y difference between defender and attacker
				int distance = 0; //

				if (Math.abs(diffX) > Math.abs(diffY)) {
					for (int j = 0; j < powerPills.size(); j++) {
						powerPills = game.getPowerPillList();

						if (defender.getLocation().getPathDistance(powerPills.get(j)) > distance && powerPills.get(j) != null)
						{
							Math.abs(diffX); //gets the difference of x-axis

						}
					}
				} else {
					for (int j = 0; j < powerPills.size(); j++) {
						powerPills = game.getPowerPillList();

						if (defender.getLocation().getPathDistance(powerPills.get(j)) > distance && powerPills.get(j) != null) {
							Math.abs(diffY);
							//gets the difference of y-axis


						}
					}
					actions[i] = defender.getNextDir(attackPosition, false);
				}


				if (defender.isVulnerable() == true) {
					if (Math.abs(x - xOfD) > Math.abs(y - yOfD)) {
						for (int j = 0; j < powerPills.size(); j++) {
							powerPills = game.getPowerPillList();

							if (defender.getLocation().getPathDistance(powerPills.get(j)) > distance && powerPills.get(j) != null) {
								Math.abs(diffY);
								//gets the difference of y-axis

							}
						}

						actions[i] = defender.getNextDir(attackPosition, true);
					} else {
						for (int j = 0; j < powerPills.size(); j++) {
							powerPills = game.getPowerPillList();

							if (defender.getLocation().getPathDistance(powerPills.get(j)) > distance && powerPills.get(j) != null) {
								Math.abs(diffX);
								//get the difference of x-axis


							}
						}

					}
					actions[i] = defender.getNextDir(attackPosition, false);

				}




			}

			if (i == 1) {
				//Sets up the intial data that I'll need
				List<Node> powerPills = game.getPowerPillList();
				Attacker pacman = game.getAttacker();
				Node attackPosition = pacman.getLocation();
				if (powerPills.size() == 0) //Will go right for the attacker if there are no powerpills
				{
					if (defender.isVulnerable() == true)
						actions[i] = defender.getNextDir(attackPosition, false);
					else actions[i] = (defender.getNextDir(attackPosition, true));

				} else
					//Checks and sees if its in a vulnerable state
					if (defender.isVulnerable() == true) {
						powerPills = game.getPowerPillList();
						int distance = 0;
						Node nearestPill = null;
						for (int j = 0; j < powerPills.size(); j++) {
							powerPills = game.getPowerPillList();
							//This iterate through the powerpill list since the command for that in this project takes up too much memory
							if (defender.getLocation().getPathDistance(powerPills.get(j)) > distance && powerPills.get(j) != null) {
								distance = defender.getLocation().getPathDistance(powerPills.get(j));
								nearestPill = powerPills.get(j);

							}
						}
						//If its vulnerable, it'll go towards the next powerpill location to ambush the attacker
						if (nearestPill != null)
							actions[i] = defender.getNextDir(nearestPill, true);
						else {
							//otherwise it will just attack the man straight up
							actions[i] = defender.getNextDir(attackPosition, true);
						}
					} else {
						//This is the main code if there are powerpills
						powerPills = game.getPowerPillList();
						Node nearestPill = null;
						int distance = 100000;
						for (int j = 0; j < powerPills.size(); j++) {
							//This is the same code as above, it will check for nearest powerpill
							powerPills = game.getPowerPillList();

							if (defender.getLocation().getPathDistance(powerPills.get(j)) < distance) {
								distance = defender.getLocation().getPathDistance(powerPills.get(j));
								nearestPill = powerPills.get(j);
							}
						}
						//Since I tested more complex defensive code and that wound up proving pretty ineffective
						//this time around, I am seeing if the attacker is near the powerpill I'm defending and if so, then they will go to it in order to cut her off.
						if (defender.getLocation().getPathDistance(nearestPill) < pacman.getLocation().getPathDistance(nearestPill)) {
							//if she isn't close, then the attacker will go right for her. Kind of like a guard dog.
							actions[i] = defender.getNextDir(attackPosition, true);
						} else {
							actions[i] = defender.getNextDir(nearestPill, true);
						}

					}
			}
		}
		return actions;
	}
}
