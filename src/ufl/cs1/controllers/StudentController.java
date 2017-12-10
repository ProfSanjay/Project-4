package ufl.cs1.controllers;
//preethi
import game.controllers.DefenderController;
import game.models.Attacker;
import game.models.Defender;
import game.models.Game;
import game.models.Node;

import java.util.List;

public final class StudentController implements DefenderController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int[] update(Game game,long timeDue)
	{
		int[] actions = new int[Game.NUM_DEFENDER];
		List<Defender> enemies = game.getDefenders();

		//Starts the loop for each ghost action
		for (int i = 0; i < actions.length; i++) {
			//defines the variables that will be used
			Defender defender = enemies.get(i);
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
		return actions;
	}
}