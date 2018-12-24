import * as fs from 'fs';
import * as path from 'path';
import * as express from 'express';

class Router {

	public startFolder: string;

	constructor(){
		this.startFolder = null;
	}

	public load(app: express.Application , folderName : string){

		if (!this.startFolder) this.startFolder = path.basename(folderName);

		fs.readdirSync(folderName).forEach((file) => {

            const fullName = path.join(folderName, file);
			const stat = fs.lstatSync(fullName);


            if (stat.isDirectory()) {
                //Recursively walk-through folders
                this.load(app, fullName);
			}
			else if (file.toLowerCase().indexOf('.map') > -1){
				console.log('skipping' + file);
			}
			else if (file.toLowerCase().indexOf('.js')) {
                //Grab path to JavaScript file and use it to construct the route
                let dirs = path.dirname(fullName).split(path.sep);

				const fullRoute:string  = '../../../' + dirs.join('/');
				dirs.splice(0,3);

                const router: express.Router = express.Router();
                //Generate the route
				const baseRoute: string = '/' + dirs.join('/');
				//Load the JavaScript file ("controller") and pass the router to it
				//const con: string = fullRoute + '/' +  path.basename(file , '.js');

				console.log('con = ../controllers' + baseRoute + '/' + path.basename(file , '.js'));
				console.log('base =' + baseRoute);
				var con: string = '../controllers' + baseRoute + '/' + path.basename(file , '.js')
				var controllerClass = require(con).default;

				//var controllerClass = require('../controllers/api/search/search').default;
                const controller = new controllerClass(router);

				//Associate the route with the router
                app.use(baseRoute, router);
            }
        });
    }

}

export default new Router();

