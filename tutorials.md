---
layout: page
title: Tutorials
permalink: /tutorials/
---

If you haven’t already, [install](/c-toolbox-commons/install) the Toolbox Common plugin into Eclipse.

## C Toolbox Commons Shell Queries
To import the C Toolbox Commons common queries on the Atlas Shell you must first add the C Toolbox Commons plugin to the shell's dependencies and then import the `Queries` class.

1. First if the shell is not already open then open the Atlas Shell by navigating to `Atlas` &gt; `Open Atlas Shell`.

2. Open the Atlas Shell Settings (by clicking on the gear icon).

![Atlas Shell Settings](/c-commons-toolbox/images/tutorials/shell_settings.png)

3. Search for the C Toolbox Commons plugin by typing "com.ensoftcorp.open.c.commons" in the search bar. Select the plugin and click the `Add` button.

![Search Plugins](/c-commons-toolbox/images/tutorials/search_plugins.png)

![Add Plugin](/c-commons-toolbox/images/tutorials/add_plugin.png)

4. Select the Initialization Script tab in the Atlas Shell Settings window. At the bottom add a new line with the contents `import com.ensoftcorp.open.c.commons.Queries._` and press the `OK` button.

![Import Queries Initialization](/c-commons-toolbox/images/tutorials/initialization.png)

5. You can now run any of the methods in the [Queries](https://ensoftcorp.github.io/c-toolbox-commons/javadoc/index.html) class. Example usage of the queries can be found below. 

### Query Usage

| **Function**    | *functions*                                                                                                                                                                     |
|:-----------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Parameters**  | Parameters (functionNames): A list of function names as Strings                                                                                                                 |
| **Description** | Returns the set of functions where their names matches the any of the names given the *functionNames* list. A (\*) in *functionNames* represents a wildcard that matches any string. |
| **Example&nbsp;(1)** | *Return the functions named "dswrite" and "dsread"*,                                                                                                                            |
|                 | var funcs = functions(“dswrite”, “dsread”)                                                                                                                                      |
|                 | show(funcs)                                                                                                                                                                     |
| **Example&nbsp;(2)** | Return all functions where their names start with/match "ds\*" or "dg\*"                                                                                                    |
|                 | var funcs = functions("ds\*", "dg\*")                                                                                                                                             |
|                 | show(funcs)                                                                                                                                                                     |

## C Toolbox Commons Smart Views
To open the C Toolbox Commons Smart Views open the Atlas Smart Views window by navigating to `Atlas` &gt; `Open Atlas Smart View`. The C Toolbox Commons project contributes smart views which are accessible from the dropdown menu in the bottom left of the smart view window.

