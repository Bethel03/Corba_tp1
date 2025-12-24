FROM python:3.10-slim

ARG OMNIORB_VERSION=4.3.2
ARG OMNIORBPY_VERSION=4.3.2

WORKDIR /tmp/build

COPY omniorb/omniORB-${OMNIORB_VERSION}.tar.bz2 .
COPY omniorb/omniORBpy-${OMNIORBPY_VERSION}.tar.bz2 .

RUN set -euo pipefail \
    && apt-get update \
    && apt-get install -y --no-install-recommends \
        bash \
        build-essential \
        bison \
        flex \
        libbz2-dev \
        libdb-dev \
        libssl-dev \
        netcat-openbsd \
        pkg-config \
        python3-dev \
        zlib1g-dev \
    && tar -xjf omniORB-${OMNIORB_VERSION}.tar.bz2 \
    && cd omniORB-${OMNIORB_VERSION} \
    && ./configure \
    && make -j"$(nproc)" \
    && make install \
    && ldconfig \
    && cd /tmp/build \
    && tar -xjf omniORBpy-${OMNIORBPY_VERSION}.tar.bz2 \
    && cd omniORBpy-${OMNIORBPY_VERSION} \
    && ./configure --with-omniorb=/usr/local \
    && make -j"$(nproc)" \
    && make install \
    && ldconfig \
    && cd / \
    && rm -rf /tmp/build \
    && apt-get purge -y \
        build-essential \
        bison \
        flex \
        pkg-config \
        python3-dev \
    && apt-get autoremove -y \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY projets/ClientBanqueP /app/ClientBanqueP
COPY projets/ServeurBanqueP /app/ServeurBanqueP

RUN cd /app/ClientBanqueP && omniidl -bpython OperationsBancaires.idl \
    && cd /app/ServeurBanqueP && omniidl -bpython OperationsBancaires.idl \
    && mkdir -p /var/omninames

ENV PYTHONPATH="/app/ClientBanqueP:/app/ServeurBanqueP"
