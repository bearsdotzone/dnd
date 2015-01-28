# Desktop Spell Sheet
<b>Requires Java 8, sorry.</b>
<b>How to Use:</b> Have a properly formatted spell database* called SpellDatabase.txt in the same directory as the .jar file.

I hope most of the UI is straightforward.

On the spell slot grid, a left click will increase the number and a right click will decrease it.
Logically, used spell slot numbers cannot go above those of their respective maxes.

The slider adjusts scale of the spell cards. Should also fix any broken font sizes.

Spells are added from left to right. A double click will also do the trick for a single spell either added or removed.

*Properly formatted spell database:

	******
	Spell Name
	(School) Cantrip
	Everything Else
	******
	2nd Spell Name
	(x) level (School)
	Everything Else
	Etc.

The DatabaseOrganizer build will allow easy conversion of the community resource folder's spell lists to proper database format.

	1. Combine as many of the class spell lists as you want in a text document named omegaDatabase.txt in the same folder as the DatabaseOrganizer.jar
	
	2. Run DatabaseOrganizer.jar
	
	3. Delete omegaDatabase.txt and testSpellDatabase.txt
	
