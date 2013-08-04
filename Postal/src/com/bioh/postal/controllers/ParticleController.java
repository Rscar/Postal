package com.bioh.postal.controllers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bioh.postal.objects.Player;

public class ParticleController {
	
	ArrayList<ParticleEffect> particleEffects = new ArrayList<ParticleEffect>();
	
	ParticleEffect rightThrusterParticleEffect = new ParticleEffect();
	ParticleEffect leftThrusterParticleEffect = new ParticleEffect();
	
	public ParticleController(){
		
		rightThrusterParticleEffect.load(Gdx.files.internal("particles/thruster.p"), Gdx.files.internal("particles"));
		leftThrusterParticleEffect.load(Gdx.files.internal("particles/thruster.p"), Gdx.files.internal("particles"));
	
	}
	
	public void addParticleEffect(ParticleEffect effect){
		effect.start();
		particleEffects.add(effect);
		System.out.println("Particle created" + effect.getEmitters().get(0).getX());
	}
	
	public void update(float delta, Player player){
		
		//a particle effect is constructed of particle emitters
		//particle emitters generate particles at certain specifications
		//we loop through each emitter within each effect to set the angle and position
		//these two are just for the ship thruster
		for (int i = 0; i < rightThrusterParticleEffect.getEmitters().size; i++) {
			if (!player.rightThrusterOn()) rightThrusterParticleEffect.getEmitters().get(i).allowCompletion();
			else rightThrusterParticleEffect.getEmitters().get(i).start();
			
			rightThrusterParticleEffect.getEmitters().get(i).getAngle().setHigh(player.getRotation()-20, player.getRotation()+20);
			rightThrusterParticleEffect.getEmitters().get(i).getAngle().setLow(player.getRotation()-20, player.getRotation()+20);
	    }
		
		for (int i = 0; i < leftThrusterParticleEffect.getEmitters().size; i++) {
			if (!player.leftThrusterOn()) leftThrusterParticleEffect.getEmitters().get(i).allowCompletion();
			else leftThrusterParticleEffect.getEmitters().get(i).start();
			
			leftThrusterParticleEffect.getEmitters().get(i).getAngle().setHigh(player.getRotation()-20, player.getRotation()+20);
			leftThrusterParticleEffect.getEmitters().get(i).getAngle().setLow(player.getRotation()-20, player.getRotation()+20);
	    }
		
		
		rightThrusterParticleEffect.setPosition(player.getRightThrusterPosition().x, player.getRightThrusterPosition().y);
		leftThrusterParticleEffect.setPosition(player.getLeftThrusterPosition().x, player.getLeftThrusterPosition().y);
		
		rightThrusterParticleEffect.update(delta);
		leftThrusterParticleEffect.update(delta);
		
		//update the particle effect
		for (int i = 0; i < particleEffects.size(); i++){
			particleEffects.get(i).update(delta);
		}
	}
	
	public void draw(SpriteBatch batch){
		
		//draw everything
		rightThrusterParticleEffect.draw(batch);
		leftThrusterParticleEffect.draw(batch);
		
		for (int i = 0; i < particleEffects.size(); i++){
			particleEffects.get(i).draw(batch);
		}
	}

}
