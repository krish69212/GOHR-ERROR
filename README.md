# GOHR ML Experiments Simple Version

This repository contains the implementation of RL Agents for learning rule-based games.

## Setup and Installation

Clone the repository:
```bash
git clone https://github.com/SkySuperCat/OLDGOHR.git
cd access-3395249-mm/reproducibility
```

You can set up the required environment using mamba (or conda):
```bash

mamba env create -f environment.yml
mamba activate gohr
```

## Running Experiments
To run an DQN experiment:
```bash
cd access-3395249-mm/reproducibility
./dqn.sh
```
This script will use the default configuration from `params/dqn.yaml`.

You will need to change the rule file path in dqn.yaml and experiment_driver.py to your actual path.

