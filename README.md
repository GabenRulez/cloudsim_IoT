# A renewable energy extention of Cloudsim Simulator #

This fork introduces usage of energy from photovoltaic farms to the Cloudsim Simulator.
User can import data from BSRN database, define the parameters of a farm and create their own examples.

# Introduced classes #
* BSRNDataLoader - parses data from BSRN datasets
* PhotovoltaicFarm - contains specifics of a photovoltaic farm
* RenewableEnergySource - computes energy based on data from BSRNDataLoader and PhotovoltaicFarm

# Modified #
* PowerDataCenter now keeps track of renewable energy usage

# Running examples #
Some existing examples were changed and now include the usage of renewable energy. They can be found in cloudsim-examples.  


# Authors #

* Anna Nosek
* Kamil Krzempek
* Piotr Kasprzyk
* Wojciech Kosztyła

Created as a project for Internet of Things course on AGH University of Science and Technology.

