# vim: ft=dockerfile

FROM jenkins:2.7.1

# Install necessary packages
USER root
RUN apt-get update \
      && apt-get install -y sudo \
      && apt-get install -y sudo supervisor \
      && rm -rf /var/lib/apt/lists/* \
      && echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers

# Install docker-engine
ARG docker_version=1.11.2
RUN curl -sSL https://get.docker.com/ | sh && \
    apt-get purge -y docker-engine && \
    apt-get install docker-engine=${docker_version}-0~jessie

# Make sure jenkins user has docker privileges
RUN usermod -aG docker jenkins

# Install initial plugins
USER jenkins
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt

# supervisord
USER root


# Create log folder for supervisor and jenkins
RUN mkdir -p /var/log/supervisor
RUN mkdir -p /var/log/jenkins

# Copy the supervisor.conf file into Docker
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# Start supervisord when running the container
CMD /usr/bin/supervisord -c /etc/supervisor/conf.d/supervisord.conf
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
