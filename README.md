# TESV_Alchemy_Recipe_Browser
A standalone JavaFX application for examining alchemy recipes in TESV: Skyrim

### Overview:
As the description suggests, this is a standalone Java application for examining alchemy recipes in Skyrim. The general idea of the application is as follows:
  1) Define your character's stats with the 'Character' tab (currently vanilla Skyrim and Ordinator are supported).
  2) Select 1 - 5 effects which must be on the potion, and search.
  3) After returning results, you may define additional criteria, such as requiring all ingredients to be from a certain extension (DLC) of the game, or requiring all ingredients to be harvestable.
  
  (A more detailed overview of the application is available in the context menu -> Help)
  
### Application setup for end users:
  1) Download this repository <TODO - is it possible to include built JAR for download?>
  2) Extract to desired location on your computer.
  3) In ..\TESV_Alchemy_Recipe_Browser\ARB_Data\binaries\, find FuturaCondensedBQ-Medium.otf and run it. This will download the application's font to your computer. This step is technically optional, but the style for the application is aligned with this font's size, so other fonts might not work 100% correctly.
  4) In ..\TESV_Alchemy_Recipe_Browser\ARB_Data\binaries\, find and execute TESV_Alchemy_Recipe_Browser.jar <TODO - can this be an .EXE?>. This should launch the base application.
  5) Within the application open the context menu -> Help for more detailed usage instructions.
  
### Application setup for developers:
  1) Clone this repository. _NOTE: I used Eclipse for development, so guidelines will be defined for this IDE. However, you could use any other Java IDE as desired._
  2)  In ..\TESV_Alchemy_Recipe_Browser\ARB_Data\binaries\, find FuturaCondensedBQ-Medium.otf and run it. This will download the application's font to your computer. This step is technically optional, but the style for the application is aligned with this font's size, so other fonts might not work 100% correctly.
  3) In Eclipse, open Project -> Properties -> Java Build Path -> Libraries. Click 'Add External JARs'. Navigate to ..\TESV_Alchemy_Recipe_Browser\ARB_Data\binaries\. Then add jfxrt.jar and all 3 .jars that start with log4j.
  4) Click "Apply" or "Apply and Close", depending on your Eclipse version.
  5) After a clean, you should be able to run the base class "ARB.java" from within Eclipse.
