@import forms.ConferenceList
@(list: List[Conference],flash: play.mvc.Http.Flash)
@import helper._


@main {

<h2>Conferences</h2>
<form method="GET" action="/conferences">
    Search Conferences:
    <br>
    <br>
    <select id="conf_status", name="conf_status">
        <option value="all">All Status</option>
        <option value="active">Active</option>
        <option value="archived">Archived</option>
    </select>
    <input type="search" id="searchbox"  placeholder="Conference name">
    <input type="submit" id="searchsubmit" value="Search" class="btn primary">
</form>
<h2 id="homeTitle">Conferences found: @list.size()</h2>
<table class="table-condensed zebra-striped">
    <thead>
    <tr>
        <th>Conference acronym</th>
        <th>Location</th>
        <th>Date</th>
        <th>Status</th>
    </tr>
    </thead>

    <tbody>

    </tbody>
</table>
}