import App from '../../server';


export class DBSearch{



	public getRegions(callback){
		console.log('querying database for regions');
		App.db.query('SELECT region_name FROM world.region limit $1', ['10'] , function(err,result) {
			if(err){
				console.log(err);
				return callback(err);
			}
			return callback(null , result);
		});


	}

}
