<AGPL 3.0 License>
> The Forecaster application is a financial assistance helper. It allows
> a user to create, edit, delete Financial Events that may be one-time and
> reoccurring Income or Expense events. These events can then be used to
> generate projections in either a ledger, or graphical format.

> Copyright (C) 2010-2012  David Clark

> This program is free software: you can redistribute it and/or modify
> it under the terms of the GNU Affero General Public License as
> published by the Free Software Foundation, either version 3 of the
> License, or (at your option) any later version.

> This program is distributed in the hope that it will be useful,
> but WITHOUT ANY WARRANTY; without even the implied warranty of
> MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
> GNU Affero General Public License for more details.

> You should have received a copy of the GNU Affero General Public License
> along with this program.  If not, see <http://www.gnu.org/licenses/>.

</AGPL 3.0 License>

TIP: Create a one-time event called INITIAL BALANCE, and set the date equal to the Start Date on the Ledger Tab (e.g., today's date).

Security and data isolation is enforced using Java's Servlet Security model in conjunction with Google's User API.
### Manage Tab ###
Events that are older than Ledger Tab's start date are grayed-out.
### Ledger Tab ###
A default range is created based on current date. After that, the range is stored as a preference.
### Graph Tab ###
Used Google Visualization (currently requires Flash)
### Application Notes: ###
  * Performance is highly dependent on the client's ability to execute JavaScript.
  * Does not work in IE.
  * Graph Tab uses Flash.

See the links to the right to launch the application.