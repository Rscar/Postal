package com.bioh.postal.controllers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticleController {
	
	ArrayList<ParticleEffect> particleEffects = new ArrayList<ParticleEffect>();
	
	public ParticleController(){
		
	}
	
	public void addParticleEffect(ParticleEffect effect){
		effect.start();
		particleEffects.add(effect);
		System.out.println("Particle created" + effect.getEmitters().get(0).getX());
	}
	
	public void update(float delta){
		for (int i = 0; i < particleEffects.size(); i++){
			particleEffects.get(i).update(delta);
		}
	}
	
	public void draw(SpriteBatch batch){
		for (int i = 0; i < particleEffects.size(); i++){
			particleEffects.get(i).draw(batch);
		}
	}

}
