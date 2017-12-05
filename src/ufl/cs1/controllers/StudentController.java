package ufl.cs1.controllers;

import game.controllers.DefenderController;
import game.models.Attacker;
import game.models.Defender;
import game.models.Game;
import game.models.Node;

import java.util.List;

//TODO: Grab info about nodes and distances, and chekc to see pacman distance from it, if greater than ghost, then ghost attack, else if it is equal, ghost goes back, unless pacman is next to ghost
public final class StudentController implements DefenderController
{
	public void init(Game game) {
		//ohohohohoho
	}

	public void shutdown(Game game) { }

	public int[] update(Game game,long timeDue)
	{
		int[] actions = new int[Game.NUM_DEFENDER];
		List<Defender> enemies = game.getDefenders();
		
		//Chooses a random LEGAL action if required. Could be much simpler by simply returning
		//any random number of all of the ghosts
		for(int i = 0; i < actions.length; i++)
		{
			//Sets up the intial data
			Defender defender = enemies.get(i);
			List<Node> powerPills = game.getPowerPillList();
			Attacker pacman = game.getAttacker();
			Node attackPosition = pacman.getLocation();
			if (powerPills.size() == 0) //Will go right for the attacker if there are no powerpills
			{

				if(defender.isVulnerable() == true)
				actions [i] = defender.getNextDir(attackPosition, false);
				else actions[i] = (defender.getNextDir(attackPosition, true));

			}
			else
			if (defender.isVulnerable() == true)
			{
				powerPills = game.getPowerPillList();
				int distance = 0;
				Node nearestPill = null;
				for(int j = 0; j<powerPills.size(); j++)
				{
					powerPills = game.getPowerPillList();
						if ( defender.getLocation().getPathDistance(powerPills.get(j)) > distance && powerPills.get(j) != null)
					{
							distance = defender.getLocation().getPathDistance(powerPills.get(j));
							nearestPill = powerPills.get(j);

					}
				}
				if(nearestPill != null)
				actions[i] = defender.getNextDir(nearestPill, true);
				else{
					actions[i] = defender.getNextDir(attackPosition, true);
				}
			}
			else
				{
					powerPills = game.getPowerPillList();
					Node nearestPill = null;
					int distance = 10000;
				    for(int j = 0; j<powerPills.size(); j++)
				    {
					    powerPills = game.getPowerPillList();

                        if(defender.getLocation().getPathDistance(powerPills.get(j))<distance)
                        {
                            distance = defender.getLocation().getPathDistance(powerPills.get(j));
                            nearestPill = powerPills.get(j);
                        }
                    }
                    //TODO: Make it so they stay by the pill if pacman is near, otherwise try to attack her, and maybe have them go to the pills if there are none instead of attacking
                    actions[i] = defender.getNextDir(nearestPill, true);

                }
		}
		return actions;
	}
}