import { Request, Response } from "express";

export default [
	{
		handler: async (req: Request, res: Response) => {
			res.send("Hello world125!");
		},
		method: "get",
		path: "/api/savedSearch",
	},
];
