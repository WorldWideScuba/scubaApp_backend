import * as express from "express";
import * as db from "./db/index";
import { applyMiddleware, applyRoutes } from "./utils/index";
import middleware from "./middleware";
import errorHandlers from "./middleware/errorHandlers";
import Router from "./routes/router";
import routes from "./services";

process.on("uncaughtException", e => {
	console.log(e);
	process.exit(1);
  });
process.on("unhandledRejection", e => {
	console.log(e);
	process.exit(1);
  });



class App {

		public router: express.Application;
		public db: db.Database;

		constructor() {
			this.router = express();
			applyMiddleware(middleware , this.router);
			applyRoutes(routes, this.router);
			applyMiddleware(errorHandlers, this.router);
			//this.db = new db.Database();
			//this.routes();
			//this.database();


		}

		private middleware(): void {
		//	this.express.use(logger("dev"));
		//	this.express.use(bodyParser.json());
		//	this.express.use(bodyParser.urlencoded({ extended: false }));
		}

		private database(): void {
			this.db.open();
		}

		private routes(): void {
			Router.load( this.router, "dist/server/controllers");


			const router = express.Router();
			// placeholder route handler
			router.get("/", (req, res, next) => {

				res.send("Welcome Home!!");
			});
			this.router.use("/", router);

		}

}


const myApp = new App();

export default myApp;

