## Introduction ##

This page explains how to link an RTM contact to a phonebook contact.


## How to ##

RTM contacts are autonomous contact entries, living in the RTM universe. Often these contacts correspond directly to a phonebook entry. But how to link these?

Moloko offers 2 possibilities to tell that a phonebook contact is the same as a RTM contact.

  * **Implicit linkage by full name:**

> Every RTM contact has a full name entered. Moloko looks for this name in the phonebook and establishes a link if the name was found and **the name is unique**. This means 2 contacts with the same name which match the RTM name ere ignored. The correct contact has to get linked **explicitly**. See below. Otherwise the user has to take no actions.


  * **Explicit linkage by the user:**

> Perhaps the RTM user has entered an different name than in the phonebook. Like my friend Marvin did. This breaks implicit linkage from above. But to also be able to link the contacts, you can create a new **note** entry for your phonebook contact. This entry has to have one of the following format:
> > RTM: username - where username is the RTM username of the RTM contact to link<br>
<blockquote>RTM: fullname - where fullname is the RTM fullname of the RTM contact to link<br>
</blockquote><blockquote>For my friend Marvin which RTM username is <b>modulo42</b> and RTM fullname is <b>Fris Bee</b> I can link him by either entering:<br>
<blockquote>RTM: modulo42<br>
RTM: Fris Bee</blockquote></blockquote>

<blockquote>Thats all. Now Moloko is linked to his phonebook contact and can, e.g. show his photo.