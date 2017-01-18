---
layout: page
title: Tutorials
permalink: /tutorials/
---

If you haven’t already, [install](/c-toolbox-commons/install) the Toolbox Common plugin into Eclipse.

## C Toolbox Commons Shell Queries
To import the C Toolbox Commons common queries on the Atlas Shell you must first add the C Toolbox Commons plugin to the shell's dependencies and then import the `Queries` class.

- First if the shell is not already open then open the Atlas Shell by navigating to `Atlas` &gt; `Open Atlas Shell`.

- Open the Atlas Shell Settings (by clicking on the gear icon).

![Atlas Shell Settings](../images/tutorials/shell_settings.png)

- Search for the C Toolbox Commons plugin by typing "com.ensoftcorp.open.c.commons" in the search bar. Select the plugin and click the `Add` button.

![Search Plugins](../images/tutorials/search_plugins.png)

![Add Plugin](../images/tutorials/add_plugin.png)

- Select the Initialization Script tab in the Atlas Shell Settings window. At the bottom add a new line with the contents

        import com.ensoftcorp.open.c.commons.Queries._

and press the `OK` button.

![Import Queries Initialization](../images/tutorials/initialization.png)

- You can now run any of the methods in the [Queries](https://ensoftcorp.github.io/c-toolbox-commons/javadoc/index.html) class. Example usage of the queries can be found below. 

### Query Usage

| **Function**    | *functions*                                                                                                                                                                     |
|:-----------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Parameters**  | Parameters (functionNames): A list of function names as Strings                                                                                                                 |
| **Description** | Returns the set of functions where their names matches the any of the names given the *functionNames* list. A (\*) in *functionNames* represents a wildcard that matches any string. |
| **Example&nbsp;(1)** | <p>Return the functions named "dswrite" and "dsread"</p> <p>*var funcs = functions("dswrite", "dsread")*</p> <p>*show(funcs)*</p> |
| **Example&nbsp;(2)** | <p>Return all functions where their names start with/match "ds\*" or "dg\*"</p> <p>*var funcs = functions("ds\*", "dg\*")*</p> <p>*show(funcs)*</p> |

<br />

| **Function**    | globals                                                                                                                                          |
|:-----------------|:--------------------------------------------------------------------------------------------------------------------------------------------------|
| **Parameters**  | Parameter (names): A list of global variable names as Strings                                                                                    |
| **Description** | Returns the nodes representing the global variables given by the parameter *name*. A \* in *name* represents a wildcard that matches any string. |
| **Example&nbsp;(1)** | <p>Return the global variable named "devtab"</p> <p>*var globalVar = globals("devtab")*</p><p>*show(globalVar)*</p> |
| **Example&nbsp;(2)** | <p>Return all global variables where their names start with/match "dv\*"</p> <p>*var globalVars = globals("dv\*")*</p> <p>*show(globalVars)*</p> |

## C Toolbox Commons Smart Views
To open the C Toolbox Commons Smart Views open the Atlas Smart Views window by navigating to `Atlas` &gt; `Open Atlas Smart View`. The C Toolbox Commons project contributes smart views which are accessible from the dropdown menu in the bottom left of the smart view window.

