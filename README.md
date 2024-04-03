# Why I decided to create this app

## A bit of a history

At some moment I decided to pass TOEFL exam and get 90+ score. Excellent, but how should I prepare?
It seemed to me that I had 2 main problems:

1. Vocabulary
2. Time management on exam

Ok, to solve these problems I have to:

1. Learn new words
2. Find information about time spending during exam and then solve tasks trying to be in time

These tasks are not really hard to do:

1. I found _Anki_ app witch was I think is the best for studying vocabulary
2. There are quite a lot of videos about each TOEFL task and every tack could be checked by AI

Well, ways to learn are found but one question remains: 
How much time do I need to spend on preparations to get the score I want? I didn't find the answer 
but I was not in a hurry (had more than 3 month before exam) so it was decided to create a habit:
I need to spend 30 minutes a day on preparing for exam. But these 30 minutes will not be spend 
on studying continuously because, for example, I could learn words in 5 minutes break at university
or on a road. So, I need some time control app.

## Exploring existed apps

Firstly, I should mention that at the beginning I used time control app only for increasing 
vocabulary. And even for this task I didn't find that I wanted. I tried many different apps 
(let's not mention theirs titles, I just don't remember them). I had the following problems:

* I always forgot to stop timer. And it continues to run so after 3 hours it was already hard 
to say how much time did I really spend on vocabulary.

* Let's say today I spend 10 minutes out of 30 planned for studying. Ok, the most logical action 
would be spending extra 20 minutes on studying tomorrow. But I found such _reschedule 
functionality_ nowhere.

Here I decided that I need my own time control and habit creation app.

# Development 

## Minimum functionality that I expect from this app

I believe that it should be comfortable for me to use this app from the moment of release, may be
just for only one specific task. And for now, the goal will be vocabulary with _Anki_.

Now I think there will be following entities:

1. Event - something, that is continuous and has start and end
2. Activity - series of related non overlapping events
3. Goal - entity that have information how to measure progress

So I need to implement following functionality:

- [ ] Create new goals, activities and edit them.
- [ ] Run event (just by click on a button)
- [ ] Stop event (by click on a button or when screen is turned off)
- [ ] Some analytics
  - [ ] Time debt
  - [ ] Some graphics


## How I see this app in a future

At the peak of its development, it will be high customizable tasks control app with ability to 
measure different aspects of a task completion. For example, there could be:

- tasks that will be completing as a sum of times of other tasks
- tasks that will fill other tasks with time by some proportion 
(ex.: 0.4 for reading, 0.6 for writing)
- some other measurements (not only time but ex.: number (of words))
- server with other user's templates
- reminders about running timers (to not to forget to stop them) 
and some comfortable ways to stop them
- desktop app


# Goals

## Pet project

After half year of _Kotlin Fullstack Developer_ course and 1 year of writing app on kotlin
I still haven't got completed project that I am ready to show to other people. I think this 
happened because all those projects had quite complicated MVP (with front and back) so this app
will have as small MVP as possible and in future updates will contain mainly small changes.

## Technical goal

To try Kotlin Multiplatform. But MVP will contain only android app.

### From project wizard

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…