package ufl.cs1.controllers;
<<<<<<< HEAD
=======

>>>>>>> master
import game.controllers.DefenderController;
import game.models.Attacker;
import game.models.Defender;
import game.models.Game;
import game.models.Node;

import java.util.List;

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

		//Starts the loop for each ghost action
		for(int i = 0; i < actions.length; i++)
		{
			//Sets up the intial data that I'll need
			Defender defender = enemies.get(i);
			List<Node> powerPills = game.getPowerPillList();
			Attacker pacman = game.getAttacker();
			Node attackPosition = pacman.getLocation();
			if (powerPills.size() == 0) //Will go right for the attacker if there are no powerpills
			{
				//TODO: Change behavior to guard normal pill if the tests don't work
				if(defender.isVulnerable() == true)
					actions [i] = defender.getNextDir(attackPosition, false);
				else actions[i] = (defender.getNextDir(attackPosition, true));

			}
			else
				//Checks and sees if its in a vulnerable state
				if (defender.isVulnerable() == true)
				{
					powerPills = game.getPowerPillList();
					int distance = 0;
					Node nearestPill = null;
					for(int j = 0; j<powerPills.size(); j++)
					{
						powerPills = game.getPowerPillList();
						//This iterate through the powerpill list since the command for that in this project takes up too much memory
						if ( defender.getLocation().getPathDistance(powerPills.get(j)) > distance && powerPills.get(j) != null)
						{
							distance = defender.getLocation().getPathDistance(powerPills.get(j));
							nearestPill = powerPills.get(j);

						}
					}
					//If its vulnerable, it'll go towards the next powerpill location to ambush the attacker
					if(nearestPill != null)
						actions[i] = defender.getNextDir(nearestPill, true);
					else{
						//otherwise it will just attack the man straight up
						actions[i] = defender.getNextDir(attackPosition, true);
					}
				}
				else
				{
					//This is the main code if there are powerpills
					powerPills = game.getPowerPillList();
					Node nearestPill = null;
					int distance = 100000;
					for(int j = 0; j<powerPills.size(); j++)
					{
						//This is the same code as above, it will check for nearest powerpill
						powerPills = game.getPowerPillList();

						if(defender.getLocation().getPathDistance(powerPills.get(j))<distance)
						{
							distance = defender.getLocation().getPathDistance(powerPills.get(j));
							nearestPill = powerPills.get(j);
						}
					}
					//Since I tested more complex defensive code and that wound up proving pretty ineffective
					//this time around, I am seeing if the attacker is near the powerpill I'm defending and if so, then they will go to it in order to cut her off.
					if(defender.getLocation().getPathDistance(nearestPill)<pacman.getLocation().getPathDistance(nearestPill))
					{
						//if she isn't close, then the attacker will go right for her. Kind of like a guard dog.
						actions[i] = defender.getNextDir(attackPosition, true);
					}
					else
					{
						actions[i] = defender.getNextDir(nearestPill, true);
					}

				}
		}
		return actions;
	}
}