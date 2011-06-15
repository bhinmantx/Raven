package raven.edit.editor;

import raven.game.interfaces.IRavenBot;
import raven.game.interfaces.IRavenMap;
import raven.game.model.RavenBot;
import raven.math.Vector2D;
import raven.triggers.Trigger;

public interface ViewportDelegate {
	public void updateStatus(String status);
	public void addWalls(Vector2D[] walls);
	public void addTrigger(Trigger<IRavenBot> trigger);
	
	public Viewport getViewport();
	public void setViewport(Viewport viewport);
	
	public IRavenMap getLevel();
}
