#What works and what needs to be done

## Introduction ##

This is a collection of features which are supported and what is todo.

### This works ###
  * Read access for RTM features: see [RTMAccessLevels](https://code.google.com/p/moloko/wiki/RTMAccessLevels)
    * lists (including smart lists)
    * tasks
    * tags
    * notes
    * locations (including Google Maps and Navigation)
    * contacts (support for linking RTM contacts to phone contacts. See [RTMContactLinking](https://code.google.com/p/moloko/wiki/RTMContactLinking))

  * Write/Delete access for RTM features: see [RTMAccessLevels](https://code.google.com/p/moloko/wiki/RTMAccessLevels)
    * adding/editing/removing tasks
    * adding/editing/removing notes
    * adding/renaming/removing lists including smart lists
    * create smart lists from searches

  * Support for RTM smart add syntax: See [RTM smart add](https://www.rememberthemilk.com/services/smartadd/)
  * Support for Android integrated search dialog. Allows searching for tasks using smart search syntax from above with search history.<br>
<ul><li>Support for <b>HTTP</b> and <b>HTTPS</b> access of RTM including proxy settings and compression to save data volume.<br>
</li><li>RTM smart search syntax operators. Also used for smart lists: See <a href='http://www.rememberthemilk.com/help/answers/search/advanced.rtm'>RTM search syntax</a></li></ul>

<table><thead><th> <b>operator</b> </th><th> <b>parameter</b> </th></thead><tbody>
<tr><td> list:           </td><td> string           </td></tr>
<tr><td> name:           </td><td> string           </td></tr>
<tr><td> priority:       </td><td> string           </td></tr>
<tr><td> status:         </td><td> completed | incomplete </td></tr>
<tr><td> location:       </td><td> string           </td></tr>
<tr><td> isLocated:      </td><td> true | false     </td></tr>
<tr><td> due:            </td><td> <a href='http://www.rememberthemilk.com/help/answers/basics/dateformat.rtm'>date</a> </td></tr>
<tr><td> dueAfter:       </td><td> <a href='http://www.rememberthemilk.com/help/answers/basics/dateformat.rtm'>date</a> </td></tr>
<tr><td> dueBefore:      </td><td> <a href='http://www.rememberthemilk.com/help/answers/basics/dateformat.rtm'>date</a> </td></tr>
<tr><td> dueWithin:      </td><td> string           </td></tr>
<tr><td> completed:      </td><td> <a href='http://www.rememberthemilk.com/help/answers/basics/dateformat.rtm'>date</a> </td></tr>
<tr><td> completedAfter: </td><td> <a href='http://www.rememberthemilk.com/help/answers/basics/dateformat.rtm'>date</a> </td></tr>
<tr><td> completedBefore: </td><td> <a href='http://www.rememberthemilk.com/help/answers/basics/dateformat.rtm'>date</a> </td></tr>
<tr><td> completedWithin: </td><td> string           </td></tr>
<tr><td> added:          </td><td> <a href='http://www.rememberthemilk.com/help/answers/basics/dateformat.rtm'>date</a> </td></tr>
<tr><td> addedAfter:     </td><td> <a href='http://www.rememberthemilk.com/help/answers/basics/dateformat.rtm'>date</a> </td></tr>
<tr><td> addedBefore:    </td><td> <a href='http://www.rememberthemilk.com/help/answers/basics/dateformat.rtm'>date</a> </td></tr>
<tr><td> addedWithin:    </td><td> string           </td></tr>
<tr><td> timeEstimate:   </td><td> "(>|<)? <a href='http://www.rememberthemilk.com/help/answers/basics/timeestimateformat.rtm'>time estimation</a>" </td></tr>
<tr><td> postponed:      </td><td> string           </td></tr>
<tr><td> tag:            </td><td> string           </td></tr>
<tr><td> tagContains:    </td><td> string           </td></tr>
<tr><td> isTagged:       </td><td> true | false     </td></tr>
<tr><td> noteContains:   </td><td> string           </td></tr>
<tr><td> hasNotes:       </td><td> true | false     </td></tr>
<tr><td> isRepeating:    </td><td> true | false     </td></tr>
<tr><td> isShared:       </td><td> true | false     </td></tr>
<tr><td> sharedWith:     </td><td> string           </td></tr>
<tr><td> AND, OR, NOT, (, ) </td><td> <b>operator</b>  </td></tr></tbody></table>



<h3>TODO</h3>
<blockquote><h4>Next steps</h4>
<ul><li>allow due time/date, estimations, repeat count to enter in non-English languages<br>
</li><li>translations to Spanish, Czech</li></ul></blockquote>

<blockquote><h4>Unsupported RTM features</h4>
<ul><li>Groups</li></ul></blockquote>

<blockquote><h4>Unsupported RTM smart search syntax operators</h4>
<blockquote>Also used for smart lists: See <a href='http://www.rememberthemilk.com/help/answers/search/advanced.rtm'>RTM search syntax</a>
</blockquote><ul><li>locationWithin:<br>
</li><li>isReceived:<br>
</li><li>to:<br>
</li><li>from:<br>
</li><li>includeArchived:</li></ul></blockquote>

<blockquote><h4>nice to have</h4>
<ul><li>implement search auto completion (low prio)<br>
</li><li>Groups<br>
</li><li>RTM date format:<br>
<ul><li>missing separator \u0020\u0068\u0020|\u6642|h