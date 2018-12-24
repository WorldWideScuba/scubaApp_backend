
import * as dbsearch from '../../../db/db.actions/db.search';


export default class Search {

	public dbsearcher: dbsearch.DBSearch;

	// api/search root
	constructor(router){
		router.get('/' , this.getRegions.bind(this));
		this.dbsearcher = new dbsearch.DBSearch();
	}

	getRegions(req , res){
		console.log('**** retreiving regions');
		this.dbsearcher.getRegions((err, data) => {
			if(err){
				console.log(err);
				res.status(400).send(err);
			}
			else{
				console.log('YAY');
				res.status(200).send(data.rows);
			}
		})
	}

	//create a saved search
	postSavedSearch(req , res){

	}

	//return either one or all saved searches
	getSavedSearches(req , res){

	}

	//delete a saved search from database
	deleteSavedSearch(req, res){

	}

	//modify a saved search
	modifySavedSearch(req , res){

	}


}

