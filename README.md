# 3D-Print-Calculator
by SomeTechNoob/SomePrintrNoob/SpeedyPotato.

A simple 3D print calculator.  I wanted to improve on my simple 3D print cost calculator program which I have been using privately.  I know most slicers have this built in, but personally I use Repetier-Host which does not have any sort of cost calculation built in.  This program also factors in electricity costs, which while are minute, are a nice addition.

Instructions for the newbs:
 1. Download this repository by clicking the big green button.
 2. Extract 3D Print Calculator v2.1.0.zip.  The files in this archive are all you need to configure and run.
 3. Customize general settings in Config.txt.  These are global, meaning that only the first line of numbers are used.  One setting for the whole program.  Search up the electricity rate in your area, and customize additional costs and multipliers.
 4. Customize printers and plastics in the text files.  Follow the format listed in the first line of the text file.  You can add as many printers and filaments as you want.
 5. Run Runner.bat!  Yay!  You will need to slice the parts in your slicer first, then input data as requested.

ToDo List:
 - Config file creation if files do not exist.
 - Unit customization
 - Printer Depreciation calculation?
 - GCode Analysis?  Might be a bit down the pipeline.
 - Graphical User Interface?  Might be a bit down the pipeline.

CHANGELOG
v2.1.1 release 2016/6/21
- You can now can calculate costs based on weight(g) or length(mm).  You can change this option in Config.txt.  replace "length" with "weight" or vice versa.

v2.1.0 release 2016/6/12
 - First major revision.  3 .txt configuration files now.
 - Config.txt: General config which applies throughout the whole program.  Handles electricity rate, Minimum print price, and Profit multiplier.
 - Printers.txt: You can now add multiple printers with respective wattages.  Added in a similar way to filaments.
 - Plastics.txt: Should remain unchanged.
 - Changed default print multiplier and minimum print price from 3 to 2.  Just testing to ensure calculations still worked.

v2.0.0 release 2016/6/10
 - First release.  Re-written from scratch.
 - Features: Filament Configuration
 - Reddit thread: https://www.reddit.com/r/3Dprinting/comments/4nl5su/a_3d_print_cost_calculator_im_working_on/
