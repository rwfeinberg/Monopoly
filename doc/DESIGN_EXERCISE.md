# OOGA Lab Discussion
## Names and NetIDs:

* Wyatt Focht (wgf6)
* Muazzam Khan Noorpuri (mk412)
* Catherine Livingston (cel52)
* Sylvie Mason (sam165)
* Ryan Feinberg (rwf8)

## Fluxx

### High Level Design Ideas


### CRC Card Classes

This class's purpose or value is to manage something:
```java
 public class Something {
     public int getTotal (Collection<Integer> data)
     public Value getValue ()
 }
```

This class's purpose or value is to be useful:
```java
 public class Value {
     public void update (int data)
 }
```

### Use Cases

### Use Cases

 * A new game is started with five players, their scores are reset to 0.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

 * A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

 * Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

 * A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

 * A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```