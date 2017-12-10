package ufl.cs1.controllers;

import game.controllers.DefenderController;
import game.models.Attacker;
import game.models.Defender;
import game.models.Game;

import java.util.List;

public final class StudentController implements DefenderController {
	public void init(Game game) {
	}

	public void shutdown(Game game) {
	}


	public int[] update(Game game, long timeDue) {
		int[] actions = new int[Game.NUM_DEFENDER];
		List<Defender> enemies = game.getDefenders();

		//Chooses a random LEGAL action if required. Could be much simpler by simply returning
		//any random number of all of the ghosts
		for (int i = 0; i < actions.length; i++) {
			Defender defender = enemies.get(i);
			Attacker attacker = game.getAttacker();
			List<Integer> possibleDirs = defender.getPossibleDirs();
			if (i == 2) { //This is Madelyn's defender.  Clyde, the orange ghost.

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
		}
	return actions;
	}

			//In Vulnerable Mode, make defender go in opposite direction ghost is going.


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
        }
        else {
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
		}
		else if (attacker.getLocation().getX() > defender.getLocation().getX() && possibleDirs.indexOf(Game.Direction.RIGHT) != -1) {
			// Get attacker X location.  If it is greater than defender AND I can move to the right, then move to the right.
			actions[i] = Game.Direction.RIGHT;
		}
		else if (attacker.getLocation().getY() < defender.getLocation().getY() && possibleDirs.indexOf(Game.Direction.UP) != -1) {
			// Get attacker Y location.  If it is less than (higher up) than defender AND I can move up, then move up.
			actions[i] = Game.Direction.UP;
		}
		else if (attacker.getLocation().getY() > defender.getLocation().getY() && possibleDirs.indexOf(Game.Direction.DOWN) != -1) {
			// Get attacker Y location.  If it is greater than (lower down) defender AND I can move down, then move down.
			actions[i] = Game.Direction.DOWN;
		}
		/*else {
			// If the defender can't move in any direction that gets him closer to the attacker, move in the attacker's direction.
			actions[i] = attacker.getDirection(); //14535.1
		}*/


	}
}
