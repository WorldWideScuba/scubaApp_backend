import * as express from "express";
// import atlas from "./db/index";
import middleware from "./middleware";
import errorHandlers from "./middleware/errorHandlers";
import routes from "./routes";
import { applyMiddleware, applyRoutes } from "./utils/index";

process.on("uncaughtException", (e) => {
	console.log(e);
	process.exit(1);
});
process.on("unhandledRejection", (e) => {
	console.log(e);
	process.exit(1);
});

class App {

	public router: express.Application;

	constructor() {
		this.router = express();
		applyMiddleware(middleware , this.router);
		applyRoutes(routes, this.router);
		applyMiddleware(errorHandlers, this.router);
	}

}

const myApp = new App();

export default myApp;
