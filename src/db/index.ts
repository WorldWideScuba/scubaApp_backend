// const { Pool } = require('pg');
import * as dotenv from "dotenv";
import * as pg from "pg";

dotenv.config();

// postgres connection config
const config = {
	host: process.env.DBHOST,
	user: process.env.DBUSERNAME,
	password: process.env.DBPASSWORD,
	database: process.env.DBNAME,
	port: 5432,
	ssl: true,
	};

class Database {

	private pool: pg.Pool;

	constructor() {
		this.pool = new pg.Pool(config);
	}

	public open() {
		this.pool.connect(( err, client, done) => {
			if (err) {
				console.log("not able to get connection " + err);
			}
		} );
	}

	public query(text: string  , params: string[] , callback) {
		return this.pool.query(text, params, callback);
	}

}

const atlas = new Database();
atlas.open();
export default { atlas };
