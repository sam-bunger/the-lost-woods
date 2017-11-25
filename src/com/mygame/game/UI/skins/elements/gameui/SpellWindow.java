package com.mygame.game.UI.skins.elements.gameui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygame.game.UI.skins.tools.TextTooltip;

public class SpellWindow extends Window {
    public SpellWindow (Skin skin) {
        super("Spells", skin);

        defaults().pad(2);

        for (int i = 0; i < 10; i++) {
            Button spell = new TextButton("S" + i, skin);
            spell.addListener(new TextTooltip("This is a tooltip!\nThis spell is really important!\nSerious stats about this spell!\nThis is a tooltip!", skin));
            if(i == 4){
            	Button spellQ = new TextButton("Q", skin);
            	add(spellQ).width(78).height(78);
            }else if(i == 5){
            	Button spellE = new TextButton("E", skin);
            	add(spellE).width(78).height(78);
            }else{
            	add(spell).width(52).height(52);
            }
        }

        pack();
        setKeepWithinStage(true);
        setMovable(false);
    }
}
