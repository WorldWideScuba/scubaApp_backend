import * as express from 'express';
import * as logger from 'morgan';
import * as bodyParser from 'body-parser';
import * as db from './db/index';
import * as fs from 'fs';
import Router from './routes/router';


class App {

		public express: express.Application;
		public db: db.Database;

		constructor() {
			this.express = express();
			this.db = new db.Database();
			this.middleware();
			this.routes();
			this.database();


		}

		private middleware(): void {
			this.express.use(logger('dev'));
			this.express.use(bodyParser.json());
			this.express.use(bodyParser.urlencoded({ extended: false }));
		}

		private database(): void {
			this.db.open();
		}

		private routes(): void {
			Router.load( this.express, 'dist/server/controllers');


			const router = express.Router();
			// placeholder route handler
			router.get('/', (req, res, next) => {

				res.send('Welcome Home!!');
			});
			this.express.use('/', router);

		}

}


const myApp = new App();

export default myApp;

