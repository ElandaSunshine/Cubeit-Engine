/**
    ===============================================================
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any internal version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program. If not, see <https://www.gnu.org/licenses/>.
    
    Copyright (c) 2021 ElandaSunshine
    ===============================================================
    
    @author Elanda
    @file   GameScript.java
    @date   19, June 2021
    
    ===============================================================
 */

package xyz.elandasunshine.capi.script;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  The GameScript annotation helps the CubeIt VM to find and load any scripts contained inside the loaded game.
 *  @author elanda
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GameScript
{
	/** Specifies scripts to execute for the class. */
	Class<? extends IScript>[] value();
	
	/**
	 *  Specifies the priority when to execute this object in correlation to other scripts of this type.
	 *  Values 0 up to 99, -1 means no specified order.
	 */
	int priority = -1;
}
