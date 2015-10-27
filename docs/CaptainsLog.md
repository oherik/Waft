* [Week 3](#week3)
* [Week 4](#week4)
* [Week 5](#week5)
* [Week 6](#week6)

# <a name="week6">Week 6</a>

Git Flow
--------

An issue that Git Flow doesn't address is documentation. Documentation often needs to be updated. Maybe it has nothing to do with a certain feature, or even really with the code. In our case, we put reflections, the outcomes of meetings, etc., in a documentation folder.

So where do you push it? The obvious choice is `master`: it's the go-to place for writing that regards the application as a whole.

But what about merging to develop? There is no point in continously merging master to develop everytime a reflection text (like this one) changes – too much work, confusing to follow branches, especially since the docs have little to do with functionality.

So when it comes to docs, master and develop are not in sync. In fact, develop doesn't even have the docs, so they don't become outdated. This is a good feature. The docs don't conflict with merges from the release branch.

There is only one issue: now you can't merge a hotfix branch directly. The hotfix branch branches off from develop, so it contains all docs. If you merge it directly to develop, you get a lot of stuff that don't have anything to do with the fix. 

Our solution: do a cherry-pick – take the hotfix and cherry-pick that commit to develop. This gives you only the fix, nothing else. But you lose the merge history, just a new commit on develop. You can put in the commit message that it is a cherry pick. But you don't get the nice, clean tree structure. 

All in all, this still feels like an alright trade-off.

# <a name="week5">Week 5</a>

Sustainable Pace (Working Weekends)
-----------------------------------

We encountered a team problem of some magnitude. Two of us had worked a bit over the weekend. One uf us told the others "now I have made this thing". This created a weird power balance, and weird expectations, since we had agreed not to work on the weekend.

Now all of a sudden, we had a situation where 
1. someone had taken on a task without any consultation with the team, effectively deciding, without consulting anyone, that they would come up with the solution. I theory, this would just be *a* solution, not *the* solution. But in practice, with tight deadlines, that solution will probably live on.
2. people felt expectations had shifted. Even if there is an agreement to keep a sustainable pace, if one person starts pulling extra weight, how does that effect me. Should I feel guilty. Even if the others expect nothing, *will* I feel guilty, because that is how I feel when something like this happens? 

All in all, we saw very clearly there was a problem that needed addressing. On the one hand, we all really like coding, and it would be strange to restrict the urge to solve a current problem when it arises. On the other hand, there are the problems mentioned above. What to do?

We talked about three possible solutions:
1. Keep of the codebase on the weekend.
2. Change sprints to extend Monday to Monday, instead of Monday to Friday.
3. A more novel solution – a "Scrum, but …" kind of thing: keep the sprints as they are, but after the review and retrospecitve, run through some "weekend problems" – things that it would be good to look at, problems we'd like to solve. These are up for grabs, and are sanctioned by the group to work at during weekends. If any one of them is solved on Monday, all the better. If not, then no problem, they'll likely appear in the next sprint.

Needless to say, we opted for option 3. We will try this novel solution to a pretty strange problem, and we'll evaluate after.

More "Scrum, but …"
----------------

Not all of us are happy with the whole Sprint planning. To some degree, the group feels it would be good to be able to just grab problems and work on them, taking another one when the first one is done. What we lose with that solution is the weeky sync, the continous shipping (what if something is semi-implemented on the develop branch, and we're ready for a release in other regard? Do we pick out the semi-feature commits? Do we ship with dead code?). We decided to do antoher "Scrum, but …" by saying that some items are sprint items, that really should get done by the end of the week, giving us a feeling of a deadline. After that, there are some items that are just up for grabs, to take and work on when we want.

Testing
-------

We realized we spent a *lot* of time writing tests last week, in large part because it is hard to test some things in Android, like wifi connection and such. Unit tests don't come with a complete context for finding those things.

We will thus limit testing to "functional testing" when unit tests seem too hard to create within reasonable time. This is a prototype, we argued. And there is no point spending more time on testing than on developing, if we can be fairly sure that our code works, with more crude or high level testing than unit tests.

"Test where it makes sense. Leave the rest."

The hat
-------

We started using a hat for the product owners. When they wear it, they are product owners. When they don't, they are developers. This works pretty well, giving everyone a clear sense of when someone is in a ceertain role.

The sprint planning
------------------

The planning took a lot more time this week. Not everyone was happy with this. I (Rikard) felt that lack of sync was a problem last week, as was a lack of vision. (By the way, *have* we written a vision? Mental note: check that.) Some people felt we should be getting to work faster. What to do? How can we sync everyone's preferences? I don't know. And it absolutely might hurt morale.

One thing I could do, as Srcum master, is spending more time in Trello, splitting up and refining tasks, adding more detail, and such, to give everyone else better a better position to do a good job. I should spend less time coding, more time aligning the stars and planets in our favor.

Software architecture
---------------------

We thought about layered architecture, which we decided last week we would work with. But in the first week, we crated *no* model classes. We should probably discuss high level issues like this more inbetween us, so that integration does not become hell. This time, we threw ourselves into to coding, bit of large pieces and perhaps it was in some ways more than we could chew.

"Skynda långsamt" is probably a good motto for the upcoming weeks.

Being on time and keeping time
------------------------------

Timeliness has become an issue. Two people in the group are timely people that think it is very disrespectful to be late. Three people are less punctual and less concerned about keeping time. This is of course an issue, and perhaps the hardest nut to crack, since what we are dealing with here is a fundamental clash of personalities.

We held a ten minute meeting after todays stand up where we discussed the issue, and we didn't come up with any one solution to the problem.

Being a part in the case, I will still try to boil down, as impartially I can, the arguments on both sides:

### For timeliness

* It is a "just do it" kind of thing. If we decide on a time, just keep that time.
* Making people wait is disrespectful.
* If the people that are on time have to wait for the others, that builds irritation that lingers on. The bad mood sticks and affects the meeting coming after, perhaps the performance for the whole day.
* It's a slippery slope. If we allow five or ten minute delays, then why couldn't there be 30 minute delays? Where do we draw a line? And if we draw a line, then that just become a new boundary to break. Either you are on time, or you are not. Period.
* In the professional world, you will be expected to always be on time. Tardiness is not taken lightly in the world of business.
* If you are late, even just a little, then notify.

### For unstrict timekeeping

* It is, at its heart, a matter of personality trait. Time optimists can try to keep better time, and time pessimists can try to be more relaxed about it, but at the end of the day, people don't change.
* Having strict time rules causes unnecessary stress and irritation about being there just in time or before, when really it is not that important from the point of view of efficiency. 
* To an extent, tardy people can try to keep better time. But to an extent, timely people can manage their feelings and expectations about tardiness as well.
* People can keep tardiness reasonable. No one will start being 30 minutes late to 15 minute meeting just because we have an accepting attitude towards 5 minute delays.
* In professional life, you can't expect everyone to always keep time. You will always have to manage that some people are a little late a lot of the time.

We did not reach any conclusions. To my understanding, both sides in this argument want the other to behave and feel more like them. Both sides also feel that they are in the right here, and that their way of reasoning and behaving should be the norm. So we have a deadlock.

A similar issue was raised regarding keeping meetings within the time frame. If we draw over, then we must stop and ask everyone if that is alright, not just keep going, like yesterday, and blow more than 1 hour over schedule. Everyone thought this was a good idea. And the agreement was that everyone, both the people bothered and unbothered by this, should keep an eye out for this kind of thing.

The curse of week 1
-------------------

Distributed work is a big issue. When a project starts up, there are tons of things to do, lots of interconnected functionality, and a lot of decisions to be made that will linger on for a long time. It is really hard to work at different times in the same system. When the system is small, it is almost as if it's a room that is too small. It seems to easy to step on each others code toes. Having some people work on server and some on client is a good thing, but as we move into more and more the same files, I expect troubles.

Another thing that is the curse of week one (and probably the curse on any of these projects that will run for no more than a couple of weeks!) is that as we work together, we develop common knowledge, about both the system and each other. We develop a common vision. We might peak right when the project ends. Which is sad. I get the idea of working this intensly, but I feel a lot of knowledge about smart ways to work is yet to come, and maybe even won't come, because the project deadline will cut that knowledge short.

The Bug Backlog
---------------

Reading the [Scrum Alliance Article article on bug backlog](https://www.scrumalliance.org/community/articles/2013/october/dealing-with-bugs-in-the-product-backlog) and using our own judgement based on how we as a team work together, we decided to create a separate "Bug Backlog" in Trello. These can then be quickly added by *both* developers and Product Owners. Why let developers add to the bug backlog? Because developers run the code a lot more often, do a lot more edge case testing, and have a basic feel (like any human being) for what is to be considered a bug.

The bug that made us start using the Bug Backlog is this:

1. The user chooses to post a flag.
1. The user clicks on a flag, writes the comment and sends the flag.
1. Now, when the user returns the flagging view: if the user presses the Android back button, they will be taken through all subsequent fragments, *including the previous view for posting flags*. 

This is obviously a flag, and was discovered by a developer. Where to put it? The ice box seems to lame a place. And the bug is too small to warrant a complete rejection of this week's sprint, as we want to start user testing and hallway testing. The bug is relevant, but not relevant enough to dicredit the whole user story, which is centered around having a working prototype.

So we invented the Bug Backlog. Anyone can put a bug in here. It should be described in terms of behavior, and what the *expected* behavior is. Before a sprint, the product owner puts the bugs in the backlog, and prioritizes them. 

Why prioritize bugs, rather than just fixing them? Because the product owner might feel it is more important to add a feature than to fix a bug. But the bug might higher priority than certain features. This approach gives us a good idea of the importance of bugs, and whether the developers should spend time fixing them or just let them slide for now. The development team can still decide what to work on, as they have the best knowledge of how much time something will take to fix in proportion to the importance. 

Let the product owner prioritize, and the team decide on whether it is worth it or not. Just like a user story.

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
