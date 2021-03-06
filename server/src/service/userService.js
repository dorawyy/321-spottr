const userMultiplierData = require("../data/userMultiplierData.js");
const userData = require("../data/userData.js");

module.exports = {
    async createNewUser(sub, email, name, picture, dbConfig) {
        // Create all the data required for a new user in the database structure
        var newUserId = await userData.createUser(dbConfig, sub, email, picture, name);

        var newMultiplierId = await userMultiplierData.createUserMultipler(newUserId, dbConfig);
        await userData.upsertUserMultiplier(newUserId, newMultiplierId, dbConfig);

        return new Promise(async function(resolve){
            resolve(await userData.getUserByUserId(newUserId, dbConfig));
        });
    },

    async getAllUsers(dbConfig) {
        let data = await userData.getUsers(dbConfig);
        return new Promise(async (resolve) => resolve(data));
    },

    async getUserById(userId, dbConfig) {
        let data = await userData.getUserByUserId(userId, dbConfig);
        return new Promise(async (resolve) => resolve(data));
    }
};
