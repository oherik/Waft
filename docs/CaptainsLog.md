* [Week 3](#week3)
* [Week 4](#week4)

# <a name="week4">Week 4</a>

Sprint planning
---------------

We planned a sprint. We realized it was hard to not get into techincal details. There are some major issues with having to work distributed. Code standards is one. Coming up with good ways to work on one thing without waiting for another to get done first is another. Slicing is hard.

Estimation is really hard as well. We chose a baseline that seemed reasonable, but estimating other stuff from that baseline was difficult, since everyone had a very different idea of how much work the baseline would actually be. That distorts everything. Using planning poker helps, it gets a conversation started.

I'm scared of the concept of having a product owner as a developer. It feels like the product owner can be persuaded by impletentation aspects. We really should introduce a PO hat or something.

Using Git Flow
--------------

Git flow does have its merits, but it also has a number of issues:

* Features can be *big*. Sticking to the rule "merge only when you are done with a feature"-rule seems dangerous. Continous merges is a safguard against large scale conflicts. An alternative approach is the continous rebase, which is better. But merging often gives the others access to the tools you build.
* We have said previously that all merges to develop should me made through a pull request. After day 1 of coding, we have not done a single pull request, but many merges. So far it seem alright. But we should probably start using the PR strategy, and communicate more about our merges. Today only two people were working in the main repo, but this will soon no be the case. Another case for not using this controlled pull request way of working is that Sebastian sugested and has experience with this workflow, so why implement it when he is not available?
* In the beinning, Git Flow (or working distributed) is hard in general, beacuse there will be a lot of
    * Setting up package structure.
    * Creating very basic classes.
    * Deciding on coding standards
... which means there will be lots of stepping on each others' toes.

Coding Dicipline
---------------

* Coding dicipline is really hard to balance with a get-it-done attitude. Between planning ahead, doing refactoring and writing good tests, a lot of time goes by. I think something's gotta give.
* Coding dicipline is really hard to balance with a get-it-done attitude. Between planning ahead, doing refactoring and writing good tests, a lot of time goes by. I think something's gotta give.
* Another thing I have a problem with is keeping code to the minimum. I implement a lot of things that might not be absolutely necessary. Many "nice to haves", so to speak. This follow from my planning, and these classes might be useful later. But I should probably build according to immediate need first. Check myself more often. I did, and I thought "is what I'm doing *really* adding value to the release?" a few times, with the answer "possibly no". But still, it seemed like a good coding choice.

In general, I have a hard time *not* adding extra complexity as I'm developing. I really need to hold complexity to a *minimum*, even if I'm making things "hacky" in the process

Frameworks
----------

How much frameworks should we use? It's really difficult to guess. I'm scared of diving into something and learning something new, especially when I have 20 alotted hours. I want to use stuff I know how do. This is probably a bad idea. I spent quite a lot of hours on struggling with GSON instead of just sitting with the documentation. Take a step back so that you can get some momentum instead of hust trying to push throug a wall.

Planning
-------

Lack of planning might just have gotten the best of us. We still have problems with package names, I think (using the default, com.example.erik), and we seem to hinge too much on the FlagVechicle activity. Or maybe I'm just lacking overview. And then there's the GUI. 

All in all, I don't think these small issues could have been avoided. *However*, moving forward, I am afraid that if we don't plan carefully and deliberately, we could experience software rot according to the Broken Window principle, which is amplified by the fast mindset of Scrum, the lack of experience with the platform and the code-now-fix-later approach that Git in general, and Git flow in particular, enables. I experienced this yesterday: I felt like it "just had to work", let the code look as it may. Today, reading "The Pragmatic Programmer", I got a sting of bad concience as they wrote about pride, broken windows and the striving to be better. I especially took to heart the advice "Don't make lame excuses", as I, when I was coding something that I felt was becoming strange and complex, could hear myself excusing myself beforehand by referring to the stressful conditions. This is a good way of becoming more stressed, less happy, and (most likely) dying early from heart attack.

Let go. Think hard. Come what may. We're a great team, and we'll manage, so long as we work together. Together we stand, divided we fall, and all that hippie crap.

# <a name="week3">Week 3</a>

## What we did

### Started SCRUMing

We have started using the SCRUM method for our work, but lightly. We do

* Stand-up-meetings
* Roles (Scrummaster and product owner)

We created a backlog and prioritized it.

### Decided on working rules

We decided on ground rules to work well together.

* Be on time for everything scheduled.
* Notify as soon as you believe you might be delayed for something scheduled.
* Minimize administration. Don't book meetings other than stand-up and mission critical meetings. Remove rather than add.
* Use the calendar restrictively.

We decided a framework for our Scrumming

* A sprint is 5 days.
* Deadline for sprints are 14:00 on Fridays.
* Stand up every weekday. Time and place are in the calendar. 

### Concept development

We developed the concept

## Problems encountered

### Scrumming

Differing schedules: how can we use SCRUM to deliver even though we don't always sit together?

### Working rules

What is the consequence of breaking a rule? We discussed it breifly. We came up with "no consequence". They are about respect, not punishments. 

The people who are at a scheduled meeting at a scheduled time can make the decisions in that meeting. If you miss it, you lose your say in that issue.
