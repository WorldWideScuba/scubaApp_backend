// const { Pool } = require('pg');
import * as pg from 'pg';
import * as dotenv from 'dotenv';

dotenv.config();

// postgres connection config
const config = {
	host: process.env.DBHOST,
	// Do not hard code your username and password.
	// Consider using Node environment variables.
	user: process.env.DBUSERNAME,
	password: process.env.DBPASSWORD,
	database: 'atlas',
	port: 5432,
	ssl: true
	};

export class Database {

		pool: pg.Pool;

		constructor() {

			this.pool = new pg.Pool(config);

		}

		public open() {
					this.pool.connect(function( err, client, done) {
				if (err) {
						console.log('not able to get connection ' + err);
				}
			} );
		}

		public query(text: string  , params: string[] , callback) {
			return this.pool.query(text, params, callback);
		}


	}

// var DB = new Database();
// export default DB;
