# CircosFX

## Chord Diagram / Circos reimplementation in a JavaFX widget for GUI interface usage.

This is a Java reimplementation of the well known plot by Martin Krzywinski (http://mkweb.bcgsc.ca/). The original Perl 
program is available at http://circos.ca . This one doesn't aim to be a replacement of that wonderful piece of software 
but wants to provide an interactive and animated plot to be included in Graphical User Interfaces for Java programs.  


![Circos Plot on MM9 genome](./demo/circos_links_mm9.png "Example plot")

![Circos Plot using ribbons](./demo/Intra_chromosomal_ribbons.png "Example plot")

#### Api
I focused very much on usability of this software. You will quickly obtain a chart in two steps:
1. initialization: you construct the plot, arcs (borders) will be defined at this time.
2. loading links: you load relations among arcs.

In the step number 1 you use one of the Circos class constructors, which allows you to specify a vector of arc lengths 
or an ArcCollection object. You will also need to specify two EventHandler to associate actions to clicks but it is not
compulsory, you can also specify null.

An ArcCollection object is a complete pre built configuration representing well known genomes. 
Here a list of available classes:
* ArabidopsisTAIR10
* ChimpPT4
* DrosophilaHiresDM3
* DrosophilaLowresDM3
* DrosophilaHiresDM6
* DrosophilaLowresDM6
* HumanHG16
* HumanHG17
* HumanHG18
* HumanHG19
* HumanHG38
* MouseMM9
* MouseMM10
* RatRN4

And more, from still in progress assemblies:
* Oryzasativa
* Sorghum
* Yeast
* Zeamays
* Cat
* Cow
* Dog
* Horse

##### Examples:
`Circos widget = new Circos(new MouseMM9(), new ArcEventHandler(), new LinkEventHandler());`

`Circos widget = new Circos(new long[]{34, 56, 90, 65, 10}, null, null);`

In the step number 2 you will load links, i.e. relations, connection between two arcs. 
Just use the provided builder as below to provide all information needed.

Such relations may be drawn as links or ribbons. 
The former are thin lines that connect the median point of the two interval described, 
the latter are bands that cover the whole intervals.
When you want to work with ribbons, specify:
`widget.setDrawRibbons(true);`

When you want to adjust thickness of the link, specify:
`widget.setStrokeWidth(0.5);` 

##### Example:
```
Link l1 = new LinkBuilder()
                 .setSourceArc(0)
                 .setSourceStart(97195432)
                 .setSourceEnd(197195432)
                 .setSinkArc(3)
                 .setSinkStart(80000000)
                 .setSinkEnd(100000000)
                 .createLink();

circos.addLink(l1)
```
 
#### Build and Test
This distribution includes Gradle for building and testing which is very easy to try.
Just use:

`./gradlew jar`
[gradlew.bat on windows]

This will build a directory (build/libs) containing a jar package managing all needed dependencies.
 


`./gradlew test`  
this will trigger unit tests including some GUI integration tests that will also demonstrate you some 
of the package capabilities. 


#### _Licensing_
I allow this work to be used free of charge under the license below but I __require__ that you notify me 
dropping a message using my linkedin profile for whatever use you do of the software.

I have licensed my work with GPLv2. I know this is quite restrictive and I'm evaluating different solutions. 
So, if you have different needs, feel free to contact me.
