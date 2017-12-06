// Nic's Base Code
package ufl.cs1.controllers;

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
		
		//Chooses a random LEGAL action if required. Could be much simpler by simply returning
		//any random number of all of the ghosts
		for(int i = 0; i < actions.length; i++)
		{
			Defender defender = enemies.get(i);
			/*List<Integer> possibleDirs = defender.getPossibleDirs();
			if (possibleDirs.size() != 0)
				actions[i]=possibleDirs.get(Game.rng.nextInt(possibleDirs.size()));
			else
				actions[i] = -1;*/

			List<Node> powerPills = game.getPowerPillList();
			Attacker player = game.getAttacker();

			int countSize = powerPills.size(), powerPillContact = 0;
			int counter = 0;

			if (powerPills.get(counter) == null)
				counter++;
			if (defender.getLocation().getPathDistance(powerPills.get(counter)) < 50) {
				counter++;

				if (powerPills.get(counter) == null && powerPills.get(counter) == defender.getLocation() )
					powerPillContact++;

				if (powerPillContact == 3)
					powerPillContact = 0;

				break;
			}
			if(counter == countSize)
				counter = 0;

			actions[i] = defender.getNextDir(powerPills.get(counter), true);


		}
		return actions;
	}
}