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
				{
					//sets up relevant information and creates a copy of the powerpill list
					List<Node> powerPillsCopy = game.getPowerPillList();
					Node defensePosition = defender.getLocation();
					Node powerPill = powerPills.get(i);
					Node nearestPowerPill = defender.getTargetNode(powerPillsCopy, true);
					if(nearestPowerPill.getPathDistance(defensePosition)+1 < nearestPowerPill.getPathDistance(attackPosition)) //checks and sees if the powerpill is safe from Pacman
					{
						actions[i] = (defender.getNextDir(attackPosition, true));
					}
					else if(nearestPowerPill.getPathDistance(defensePosition) <= nearestPowerPill.getPathDistance(attackPosition))//if pacman is near it, it'll go to the next closest powerpill
					{
						actions[i] = (defender.getNextDir(nearestPowerPill, true));
					}
					else
					{
						powerPillsCopy.remove(nearestPowerPill);
						nearestPowerPill = defender.getTargetNode(powerPillsCopy, true);
						actions[i] = defender.getNextDir(nearestPowerPill, true);
					}

				if (defender.isVulnerable() == true)
				{
					actions[i] = defender.getNextDir(defender.getTargetNode(powerPills, false), true);
				}
				List<Integer> possibleDirs = defender.getPossibleDirs();
				if (possibleDirs.size() != 0)
					actions[i] = possibleDirs.get(Game.rng.nextInt(possibleDirs.size()));
				else
					actions[i] = -1;
			}
		}
		return actions;
	}
}